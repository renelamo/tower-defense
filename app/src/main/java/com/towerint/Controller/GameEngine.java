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
import com.towerint.Model.Node;
import com.towerint.Model.Projectile;
import com.towerint.Model.TemporaryPrintable;
import com.towerint.Model.Tower;
import com.towerint.Model.TowerType1;
import com.towerint.Model.Vector2;
import com.towerint.Model.Way;
import com.towerint.R;


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
    public static Context context;


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
        // Reset the score and fails and money
        score = 0;
        fails =0;
        money =0;

        way=new Way(new Node(screenX/2,0));
        way.add(screenX/2,screenY/2);
        way.add(screenX/4,screenY/2);
        way.add(screenX/4,screenY);

        //Je rajoute ici du code de test
        towers.add(new TowerType1(100,100,this));
        attackers.add(new AttackerType1(way, this));
        attackers.add(new AttackerType1(way, this));



        pauseBitmap = BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.pause_icon);
        pauseBitmap =Bitmap.createScaledBitmap(pauseBitmap, 100, 100, false);
        playBitmap= BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.play_icon);
        playBitmap = Bitmap.createScaledBitmap(playBitmap, 100, 100, false);
        playPauseDisplay=pauseBitmap;

        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }


    public void update() {
        for(Attacker attacker:attackers){
            attacker.isDead();
            if (attacker.getSpeed().getNorm() == 0 ) {fails +=1; attackers.remove(attacker); };
            /*attackersDead.add(attacker);*/
            if (attacker.getDead()){attackers.remove(attacker);
                if(attacker.getSpeed().getNorm()!=0){
                    score +=1;
                    money += attacker.getMoney();
                }
            };
            attacker.move();

        }
        if (!attackers.isEmpty()) {
            towers.get(0).faceToPoint(attackers.get(0).getPosition());
            if (towers.get(0).ableToShoot()) {
                towers.get(0).shoot(attackers.get(0));
            }
        }


        /*for(Tower tower:towers){
            tower.towerTargetsUpdate();
            if (!tower.getTargets().isEmpty()) {
                tower.faceToPoint(tower.getTargets().get(0).getPosition());
                if (tower.ableToShoot()) {
                    tower.shoot(tower.getTargets().get(0));
                }
            }


            };

         */


        for(TemporaryPrintable temporaryPrintable:temporaryPrintables){
            if (!temporaryPrintable.isAlive()){
                temporaryPrintables.remove(temporaryPrintable);
            }
        }

        for(Projectile projectile:projectiles){
            projectile.move();
            if (projectile.getSpeed().getNorm() == 0 ) {
               //TODO  afficher un cercle rouge a lendroit ou il disparait temporairement
                for (Attacker attacker:attackers){
                    if ((attacker.getPosition().diff(projectile.getPosition()).getNorm() < projectile.getRange())){
                        attacker.takeDamage(projectile.getPower());
                    }
                }
                temporaryPrintables.add(new TemporaryPrintable(projectile.getPosition(), this, R.drawable.explosion, 100));
                projectiles.remove(projectile);
                /*projectilesDead.add(projectile);*/
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

        // Scale the HUD text
        paint.setTextSize(60);
        canvas.drawText("Score :" + score, 10, 70, paint);
        //canvas.drawLine(left, top, right, bottom, paint);

        canvas.drawText("Fails :" + fails, 350, 70, paint);
        //canvas.drawLine(left, top, right, bottom, paint);

        canvas.drawText("Money :" + money, 600, 70, paint);
        //canvas.drawLine(left, top, right, bottom, paint);



        // Unlock the canvas and reveal the graphics for this frame
        surfaceHolder.unlockCanvasAndPost(canvas);
        canvas.drawRGB(0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
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

    @Override
    public String toString() {
        return this.getHeight()+","+this.getWidth();
    }

}

