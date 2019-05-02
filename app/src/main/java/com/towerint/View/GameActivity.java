package com.towerint.View;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.towerint.Controller.GameEngine;
import com.towerint.Model.TowerType1;


public class GameActivity extends AppCompatActivity {
    public static com.towerint.Controller.GameEngine gameEngine;
    public static boolean isTouch = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        // Create a new instance of the gameEngine class
        gameEngine = new GameEngine(this, size);


        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Make gameEngine the view of the Activity
        setContentView(gameEngine);

    }


    // Start the thread in gameEngine
    @Override
    protected void onResume() {
        super.onResume();
        gameEngine.resume();
    }

    // Stop the thread in gameEngine
    @Override
    protected void onPause() {
        super.onPause();
        gameEngine.pause();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(this, "ACTION_DOWN AT COORDS "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                isTouch = true;
                 Music music = new Music();
                music.touchMusic(GameEngine.context);
                gameEngine.towers.add(new TowerType1(X,Y,gameEngine));
                break;

            case MotionEvent.ACTION_MOVE:
                Toast.makeText(this, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                break;

            case MotionEvent.ACTION_UP:
                Toast.makeText(this, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}