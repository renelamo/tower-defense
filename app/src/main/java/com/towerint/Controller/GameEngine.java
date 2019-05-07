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
import static com.towerint.Model.Printable.distance;
import com.towerint.Model.Projectile;
import com.towerint.Model.TemporaryPrintable;
import com.towerint.Model.Tower;
import com.towerint.Model.Way;
import com.towerint.R;
import com.towerint.View.Music;


public class GameEngine extends SurfaceView implements Runnable {
    private Thread thread = null;
    public List<Tower> towers;
    public List<Attacker> attackers;
    public List<Projectile> projectiles;
    public List<TemporaryPrintable> temporaryPrintables;
    private Way way;

    // To hold a reference to the Activity
    public  Context context;

    // To hold the screen size in pixels
    public int screenX;
    public int screenY;

    // Control pausing between updates
    private long nextFrameTime;
    private long nextFrameTime2;

    public static final long FPS = 60;
    // There are 1000 milliseconds in a second
    private static final long MILLIS_PER_SECOND = 1000;

    // How many points does the player have
    public int score;
    //how many attackers pass

    public int fails;

    public int money;
    public int tower = 1; //Le type de tour a construire
    public int level = 1; //Le niveau actuel

    public boolean endlevel = false; //Si le niveau est terminé
    public boolean gg =false; //Si le joueur a gagné le niveau
    public boolean begin =false; //SI le joueur est prêt à lancer la partie
    public int nbattacker1;
    public int nbattacker2;
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
    private Bitmap tower1bis1;
    private Bitmap tower1bis;
    private Bitmap tower2;
    private Bitmap tower2bis1;
    private Bitmap tower2bis;
    private Bitmap victory;
    private Bitmap next_level;
    private Bitmap defeat;
    private Bitmap start;
    private Bitmap restart;
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

            if(updateRequired()) {
                update();
                draw();
            }
            if(begin && updateRequiredArmy()) {
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

                way=new Way(this.context,new Node(screenX/2,0));
                way.add(screenX/2,screenY/2);
                way.add(screenX/4,screenY/2);
                way.add(screenX/4,screenY);
                //towers.add(new TowerType1(100,100,this));
                //required number of unit
                nbattacker1 =5;
                break;
            case 2:
                fails =0;
                way=new Way(this.context, new Node(screenX/2,0));
                way.add(screenX/2,screenY/4);
                way.add(screenX/4,screenY/4);
                way.add(screenX/4,screenY);
                //towers.add(new TowerType1(100,100,this));
                nbattacker1 =5;
                nbattacker2 =3;
                break;
            default:
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    //Do nothing
                }

        }



        //Je rajoute ici du code de test


        int partX=(int)(screenX*.15);
        //define the scale of the pictures
        pauseBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_icon);
        pauseBitmap =Bitmap.createScaledBitmap(pauseBitmap, partX, partX, false);
        playBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.play_icon);
        playBitmap = Bitmap.createScaledBitmap(playBitmap, partX, partX, false);
        tower1= BitmapFactory.decodeResource(context.getResources(), R.drawable.tower1);
        tower1 = Bitmap.createScaledBitmap(tower1, partX, partX, false);
        tower1bis1= BitmapFactory.decodeResource(context.getResources(), R.drawable.tower1bis1);
        tower1bis1 = Bitmap.createScaledBitmap(tower1bis1, partX, partX, false);
        tower1bis= BitmapFactory.decodeResource(context.getResources(), R.drawable.tower1bis);
        tower1bis = Bitmap.createScaledBitmap(tower1bis, partX, partX, false);
        tower2= BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2);
        tower2 = Bitmap.createScaledBitmap(tower2, partX, partX, false);
        tower2bis= BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2bis);
        tower2bis = Bitmap.createScaledBitmap(tower2bis, partX, partX, false);
        tower2bis1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2bis1);
        tower2bis1 = Bitmap.createScaledBitmap(tower2bis1, partX, partX, false);
        victory= BitmapFactory.decodeResource(context.getResources(), R.drawable.victory);
        victory = Bitmap.createScaledBitmap(victory, screenY, screenY, false);
        next_level= BitmapFactory.decodeResource(context.getResources(), R.drawable.next_level);
        next_level= Bitmap.createScaledBitmap(next_level, partX, partX, false);
        defeat= BitmapFactory.decodeResource(context.getResources(), R.drawable.defeat);
        defeat= Bitmap.createScaledBitmap(defeat, screenY, screenY, false);
        start= BitmapFactory.decodeResource(context.getResources(), R.drawable.start);
        start= Bitmap.createScaledBitmap(start, partX, partX, false);
        restart= BitmapFactory.decodeResource(context.getResources(), R.drawable.restart);
        restart= Bitmap.createScaledBitmap(restart, partX, partX, false);
        playPauseDisplay=pauseBitmap;

        // Setup nextFrameTime so an update is triggered
        //  nextFrameTime = System.currentTimeMillis();
    }


    public void update() {

        if(begin) {

            //Actualisation des attaquants
            int size = attackers.size();
            for (int i = 0; i < size; ++i) {
                Attacker attacker = attackers.get(i);
                if (attacker.isArrived()) {
                    fails += 1;
                    attackers.remove(attacker);
                    --size;
                    --i;
                }
                if (attacker.isDead()) {
                    attackers.remove(attacker);
                    --size;
                    --i;
                    if (attacker.getSpeed().getNorm() != 0) {
                        score += 1;
                        money += attacker.getMoney();
                    }
                }
                attacker.move();
            }


            //actualisation des cibles des tours
            for (Tower tower : towers) {
                tower.shoot(attackers);
            }

            //retrait des images temporaires expirées
            size=temporaryPrintables.size();
            for (int i=0; i<size; ++i) {
                TemporaryPrintable temporaryPrintable = temporaryPrintables.get(i);
                if (!temporaryPrintable.isAlive()) {
                    temporaryPrintables.remove(temporaryPrintable);
                    --i;
                    --size;
                }
            }

            //Actualisation des positions de projectiles
            size=projectiles.size();
            for (int i=0; i<size; ++i) {
                Projectile projectile = projectiles.get(i);
                projectile.move();
                if (projectile.isArrived()) {
                    for (Attacker attacker : attackers) {
                        if (distance(attacker, projectile) < projectile.getRange()) {
                            attacker.takeDamage(projectile.getPower());
                        }
                    }
                    temporaryPrintables.add(new TemporaryPrintable(projectile.getPosition(), this, R.drawable.explosion, 100));

                    music.bombMusic(context);
                    projectiles.remove(projectile);
                    --size;
                    --i;
                }
            }
            //check if the level is finished and if you win or loose
            if (fails == 5) {
                endlevel = true;
                gg = false;
            } else if (attackers.isEmpty() && !endlevel) {
                level++;
                endlevel = true;
                music.bombMusic(context);
                gg = true;
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

            }*/

            drawButtons();

            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
            canvas.drawRGB(0, 0, 0);
        }
    }
    //create the required army
    public void updatearmy() {
        if (begin) {
            if (nbattacker1 > 0) {
                attackers.add(new AttackerType1(way, this));
                //  attackers.add(new AttackerType2(way, this));
                nbattacker1--;
            }
            if (nbattacker2 > 0) {
                attackers.add(new AttackerType2(way, this));
                //  attackers.add(new AttackerType2(way, this));
                nbattacker2--;
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
    public boolean updateRequiredArmy() {
//*
        // Are we due to update the frame
        if(nextFrameTime2 <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime2 =System.currentTimeMillis() + 30*MILLIS_PER_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;//*/
    }

    private void drawButtons(){
        int partX=(int)(screenX*.15);
        canvas.drawBitmap(playPauseDisplay, (int)(screenX-partX), 0, paint);
        if(tower!=1) {
            canvas.drawBitmap(tower1bis1, 0, (int) (screenY - partX), paint);
        }
        else if(tower == 1){
            canvas.drawBitmap(tower1bis, 0, (int) (screenY - partX), paint);
        }
        if(tower!=2) {
            canvas.drawBitmap(tower2bis1, (int) (partX), (int) (screenY - partX), paint);
        }
        else if(tower==2){
            canvas.drawBitmap(tower2bis, (int) (partX), (int) (screenY - partX), paint);
        }
        canvas.drawBitmap(start, 2*(int)(partX), (int)(screenY-partX), paint);
        // Scale the HUD text
        paint.setTextSize(partX/3);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Score :" + score, (int)(screenX-2.5*partX), partX/2, paint);
        //canvas.drawLine(left, top, right, bottom, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Fails :" + fails, (int)(screenX-3.5*partX), partX/2, paint);
        //canvas.drawLine(left, top, right, bottom, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Money :" + money, (int)(screenX-4.5*partX), partX/2, paint);
        //canvas.drawLine(left, top, right, bottom, paint);

        // victory or defeat ?
        if(endlevel&& !gg){
            canvas.drawBitmap(defeat, screenX/2-defeat.getWidth()/2, screenY/2-defeat.getHeight()/2, paint);
            canvas.drawBitmap(restart, screenX-partX, screenY-partX, paint);
        }
        else if (attackers.isEmpty() && endlevel) {
            canvas.drawBitmap(victory,screenX/2-victory.getWidth()/2, screenY/2-victory.getHeight()/2, paint);
            canvas.drawBitmap(next_level, screenX-partX, screenY-partX, paint);

        }

    }

    @Override
    public String toString() {
        return this.getHeight()+","+this.getWidth();
    }
}

