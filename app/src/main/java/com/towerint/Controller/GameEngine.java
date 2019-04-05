package com.towerint.Controller;

import android.content.Context;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.view.Window;

import com.towerint.Model.Attacker;
import com.towerint.Model.AttackerType1;
import com.towerint.Model.Node;
import com.towerint.Model.Tower;
import com.towerint.Model.TowerType1;
import com.towerint.Model.Way;


public class GameEngine extends SurfaceView implements Runnable {
    private Thread thread = null;

    List<Tower> towers;
    List<Attacker> attackers;
    Way way;

    // To hold a reference to the Activity
    private Context context;


    // For tracking movement Heading
    public enum Heading {UP, RIGHT, DOWN, LEFT}
    // Start by heading to the right
    private Heading heading = Heading.RIGHT;

    // To hold the screen size in pixels
    public static int screenX;
    public static int screenY;

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
    public String score;

    // Everything we need for drawing
// Is the game currently playing?
    private volatile boolean isPlaying;

    // A canvas for our paint
    private Canvas canvas;

    // Required to use canvas
    private SurfaceHolder surfaceHolder;

    // Some paint for our canvas
    private Paint paint;

    private Bitmap bitmap;



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
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }


    public void newGame() {
        // Reset the score
        score = "0";

        way=new Way(new Node(0,0));
        way.add(500,500);
        way.add(0,500);
        way.add(0,0);

        //TODO: je rajoute ici du code de test
        towers.add(new TowerType1(100,100,this));
        attackers.add(new AttackerType1(100, 100, this));
        //Attacker attacker2=new AttackerType1(500,500,this);
        //attackers.add(attacker2);
        for(Attacker attacker:attackers){
            attacker.follow(way);
        }

        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }


    public void update() {
        for(Attacker attacker:attackers){
            attacker.move();
        }
        towers.get(0).faceToPoint(attackers.get(0).getPosition());
    }




 public void draw() {
    // Get a lock on the canvas
    if (surfaceHolder.getSurface().isValid()) {
    canvas = surfaceHolder.lockCanvas();

    // Fill the screen with color
    canvas.drawColor(Color.argb(255, 255, 255, 255));

    //affichage de tous les printables
    for(Tower tower:towers){
        tower.draw(canvas, paint);
    }
    for(Attacker attacker:attackers){
        attacker.draw(canvas, paint);
    }

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

    // Scale the HUD text
    paint.setTextSize(60);
    canvas.drawText("Score :" + score, 10, 70, paint);
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


}

