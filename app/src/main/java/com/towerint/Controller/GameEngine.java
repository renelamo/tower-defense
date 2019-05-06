package com.towerint.Controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;

import com.towerint.Model.Attacker;
import com.towerint.Model.AttackerType1;
import com.towerint.Model.AttackerType2;
import com.towerint.Model.Node;
import com.towerint.Model.Projectile;
import com.towerint.Model.TemporaryPrintable;
import com.towerint.Model.Tower;
import com.towerint.Model.TowerType1;
import com.towerint.Model.Way;
import com.towerint.R;
import com.towerint.View.GameActivity;
import com.towerint.View.Music;
import java.util.concurrent.ThreadLocalRandom;




public class GameEngine extends SurfaceView implements Runnable {
    private Thread thread = null;
    public List<Tower> towers;
    //static pour etre utilisé par Tower
    public static List<Attacker> attackers;
    public List<Projectile> projectiles;
    public List<TemporaryPrintable> temporaryPrintables;
    public List<Attacker> attackersDead;
    public List<Projectile> projectilesDead;
    Way way;

    // To hold a reference to the Activity TODO: Apaparemment il ne faut pas mettre de Context en static
    public  static Context context;


    // For tracking movement Heading
    public enum Heading {UP, RIGHT, DOWN, LEFT}
    // Start by heading to the right
    private Heading heading = Heading.RIGHT;

    // To hold the screen size in pixels
    public int screenX;
    public int screenY;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;

    // Control pausing between updates
    private long nextFrameTime;
    private long nextFrameTime2;
    // Update the game 10 times per second
    public static final long FPS = 60;
    // There are 1000 milliseconds in a second
    private static final long MILLIS_PER_SECOND = 1000;

// We will draw the frame much more often

    // How many points does the player have
    public int score;
    //how many attackers pass

    public int fails;

    public int money;
    public int tower = 1;
    public int level = 1;
    public boolean endlevel = false;
    public boolean gg =false;
    public boolean begin =false;
    public int nbattacker1;
    // Everything we need for drawing
// Is the game currently playing?
    private volatile boolean isPlaying;

    // A canvas for our paint
    private Canvas canvas;

    // Required to use canvas
    private SurfaceHolder surfaceHolder;

    // Some paint for our canvas
    private Paint paint;

    private Bitmap pauseBitmap;
    private Bitmap playBitmap;
    private Bitmap playPauseDisplay;
    private Bitmap tower1;
    private Bitmap tower2;
    private Bitmap victory;
    private Bitmap next_level;
    private Bitmap defeat;
    private Bitmap start;
    Music music = new Music();


    public GameEngine(Context context, Point size) {
        super(context);

        this.context = context;

        screenX = size.x;
        screenY = size.y;

        // Initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        //Initialisation des listes de tours et attaquants
        towers=new LinkedList<>();
        attackers=new LinkedList<>();
        projectiles=new LinkedList<>();
        temporaryPrintables=new LinkedList<>();

        // Start the game
        newGame();
    }

    @Override
    public void run() {

        while (isPlaying) {

            // Update 10 times a second
            if(updateRequired()) {
                update();
                draw();
            }
            if(updateRequiredarmy()) {
                updatearmy();
            }

        }
    }

    public void pause() {
        isPlaying = false;
        playPauseDisplay=playBitmap;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        playPauseDisplay=pauseBitmap;
        thread = new Thread(this);
        thread.start();
    }


    public void newGame() {

        //check the level
        switch(level){
            case 1:
                // Reset the score and fails and money
                score = 0;
                fails =0;
                money =500;
                //creation of the way
                //int randomNum = ThreadLocalRandom.current().nextInt(1, 5 + 1)*100;
                //int randomNum2 = ThreadLocalRandom.current().nextInt(1, 5 + 1)*100;

                way=new Way(new Node(screenX/2,0));
                way.add(screenX/2,screenY/2);
                //way.add(screenX/2,randomNum);
                way.add(screenX/4,screenY/2);
                //way.add(randomNum2+screenX/2,randomNum);
                way.add(screenX/4,screenY);
                towers.add(new TowerType1(100,100,this));
                //required number of unit
                nbattacker1 =5;
                    break;
            case 2:
                fails =0;
                way=new Way(new Node(screenX/2,0));
                way.add(screenX/2,screenY/4);
                way.add(screenX/4,screenY/4);
                way.add(screenX/4,screenY);
                towers.add(new TowerType1(100,100,this));
                nbattacker1 =5;
                break;

        }



        //Je rajoute ici du code de test



        //define the scale of the pictures
        pauseBitmap = BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.pause_icon);
        pauseBitmap =Bitmap.createScaledBitmap(pauseBitmap, 100, 100, false);
        playBitmap= BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.play_icon);
        playBitmap = Bitmap.createScaledBitmap(playBitmap, 100, 100, false);
        tower1= BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.tower1);
        tower1 = Bitmap.createScaledBitmap(tower1, 100, 100, false);
        tower2= BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.tower2);
        tower2 = Bitmap.createScaledBitmap(tower2, 100, 100, false);
        victory= BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.victory);
        victory = Bitmap.createScaledBitmap(victory, 1000, 1000, false);
        next_level= BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.next_level);
        next_level= Bitmap.createScaledBitmap(next_level, 100, 100, false);
        defeat= BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.defeat);
        defeat= Bitmap.createScaledBitmap(defeat, 1000, 1000, false);
        start= BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.start);
        start= Bitmap.createScaledBitmap(start, 100, 100, false);
        playPauseDisplay=pauseBitmap;

        // Setup nextFrameTime so an update is triggered
      //  nextFrameTime = System.currentTimeMillis();
    }


    public void update() {

if(begin) {
    int size = attackers.size();
    for (int i = 0; i < size; ++i) {
        Attacker attacker = attackers.get(i);
        if (attacker.getSpeed().getNorm() == 0) {
            fails += 1;
            attackers.remove(attacker);
            --size;
            --i;
        }
        ;
        /*attackersDead.add(attacker);*/
        if (attacker.isDead()) {
            attackers.remove(attacker);
            --size;
            --i;
            if (attacker.getSpeed().getNorm() != 0) {
                score += 1;
                money += attacker.getMoney();
            }
        }
        ;
        attacker.move();

    }
    if (!attackers.isEmpty()) {
        for (Tower tower : towers) {
            if (!tower.getTargets().isEmpty()) {
                if ((tower.getPosition().diff(tower.getTargets().get(0).getPosition()).getNorm() < tower.getRange())) {

                    tower.faceToPoint(tower.getTargets().get(0).getPosition());
                    if (tower.ableToShoot()) {
                        tower.shoot(tower.getTargets().get(0));
                    }
                }

            }

            if (tower.ableToShoot()) {
                tower.towerTargetsUpdate(attackers);
            }


        }
        ;
        // towers.get(0).faceToPoint(attackers.get(0).getPosition());
        // if (towers.get(0).ableToShoot()) {
        //towers.get(0).shoot(attackers.get(0));
        //  }
    }


        /*    for (Tower tower : towers) {
                if (!tower.getTargets().isEmpty()) {
                    tower.faceToPoint(tower.getTargets().get(0).getPosition());
                    if (tower.ableToShoot()) {
                        tower.shoot(tower.getTargets().get(0));
                    }

                }
                if (tower.ableToShoot()){
                    tower.towerTargetsUpdate(attackers);
                }

            }
            ;
*/


    for (TemporaryPrintable temporaryPrintable : temporaryPrintables) {
        if (!temporaryPrintable.isAlive()) {
            temporaryPrintables.remove(temporaryPrintable);
        }
    }

    for (Projectile projectile : projectiles) {
        projectile.move();
        if (projectile.getSpeed().getNorm() == 0) {
            for (Attacker attacker : attackers) {
                if ((attacker.getPosition().diff(projectile.getPosition()).getNorm() < projectile.getRange())) {
                    attacker.takeDamage(projectile.getPower());
                }
            }
            temporaryPrintables.add(new TemporaryPrintable(projectile.getPosition(), this, R.drawable.explosion, 100));

            music.bombMusic(GameEngine.context);
            projectiles.remove(projectile);
            /*projectilesDead.add(projectile);*/
        }
    }

}



    }

 public void draw() {
    // Get a lock on the canvas
    if (surfaceHolder.getSurface().isValid()) {
        canvas = surfaceHolder.lockCanvas();

        // Fill the screen with color
        canvas.drawColor(Color.GREEN);

        //dessine le chemin
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(10);
        way.draw(canvas, paint);
        //affichage de tous les printables
        for(Tower tower:towers){
            tower.draw(canvas, paint);
        }
        for(Attacker attacker:attackers){
            attacker.draw(canvas, paint);
        }
        for(Projectile projectile:projectiles){
            projectile.draw(canvas, paint);
        }

        for(TemporaryPrintable temporaryPrintable:temporaryPrintables){
            temporaryPrintable.draw(canvas,null);
        }

        /*for(Attacker attacker:attackersDead){
            //TODO ANIMATION MORT
            projectilesDead.remove(attacker);

        }
        for(Projectile projectile:projectilesDead){
            //TODO ANIMATION DESTRUCTION
            projectilesDead.remove(projectile);
        }
        */

        /*
        paint.setColor(Color.RED);
        //paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(20);

        float left = 100;
        float top = 100;
        float right = 800;
        float bottom = 1200;

        canvas.drawLine(left, top, right, bottom, paint);
        */

        canvas.drawBitmap(playPauseDisplay, screenX-100, 0, paint);
        canvas.drawBitmap(tower1, 0, screenY-100, paint);
        canvas.drawBitmap(tower2, 100, screenY-100, paint);
        canvas.drawBitmap(start, 200, screenY-100, paint);
        // Scale the HUD text
        paint.setTextSize(60);
        canvas.drawText("Score :" + score, 10, 70, paint);
        //canvas.drawLine(left, top, right, bottom, paint);

        canvas.drawText("Fails :" + fails, 350, 70, paint);
        //canvas.drawLine(left, top, right, bottom, paint);

        canvas.drawText("Money :" + money, 600, 70, paint);
        //canvas.drawLine(left, top, right, bottom, paint);

        // victory or defeat ?
        if(endlevel==true&& gg==false){
            canvas.drawBitmap(defeat, 0, screenY/4, paint);
        }
        else if (attackers.isEmpty() && endlevel ==true) {
            canvas.drawBitmap(victory, 0, screenY/4, paint);
            canvas.drawBitmap(next_level, screenX-100, screenY-100, paint);

        }


        // Unlock the canvas and reveal the graphics for this frame
        surfaceHolder.unlockCanvasAndPost(canvas);
        canvas.drawRGB(0, 0, 0);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
    }
}
//create the required army
    public void updatearmy() {
        if (begin) {
            if (nbattacker1 > 0) {
                attackers.add(new AttackerType1(way, this));
                //  attackers.add(new AttackerType2(way, this));
                nbattacker1--;
            } else {

                //check if the level is finished and if you win or loose
                if (fails == 5) {
                    endlevel = true;
                    gg = false;
                } else if (attackers.isEmpty() && endlevel == false && fails != 5) {
                    level++;
                    endlevel = true;
                    music.bombMusic(GameEngine.context);
                    gg = true;
                }
            }
        }
    }

    public boolean updateRequired() {

        // Are we due to update the frame
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }
    //frequency of invocation of the army
    public boolean updateRequiredarmy() {

        // Are we due to update the frame
        if(nextFrameTime2 <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime2 =System.currentTimeMillis() + 30*MILLIS_PER_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return this.getHeight()+","+this.getWidth();
    }


}

