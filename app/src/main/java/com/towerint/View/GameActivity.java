package com.towerint.View;

import android.content.Context;
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
import com.towerint.Model.TowerType2;
import com.towerint.R;


public class GameActivity extends AppCompatActivity {
    public static com.towerint.Controller.GameEngine gameEngine;
    public static boolean isTouch = false;
    private boolean paused=false;

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
        setContentView(R.layout.activity_game);
        setContentView(gameEngine);


    }

    @Override
    public void onBackPressed() {
        onPause();
    }

    // Start the thread in gameEngine
    @Override
    protected void onResume() {
        super.onResume();
        paused=false;
        gameEngine.resume();
    }

    // Stop the thread in gameEngine
    @Override
    protected void onPause() {
        super.onPause();
        paused=true;
        gameEngine.pause();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                if(X>gameEngine.screenX-100 && Y<100){
                    if(paused)
                    {
                        onResume();
                    }else {
                        onPause();
                    }
                }
                else if (gameEngine.tower == 1 && gameEngine.money >=100 && Y<gameEngine.screenY-100 && gameEngine.endlevel==false){

                    Music music = new Music();
                    music.touchMusic(GameEngine.context);
                    gameEngine.towers.add(new TowerType1(X,Y,gameEngine));
                    gameEngine.money = gameEngine.money - 100;
                }
                else if (gameEngine.tower == 2 && gameEngine.money >=200&& Y<gameEngine.screenY-100&&gameEngine.endlevel==false){
                    Music music = new Music();
                    music.touchMusic(GameEngine.context);
                    gameEngine.towers.add(new TowerType2(X,Y,gameEngine));
                    gameEngine.money = gameEngine.money - 200;
                }
                else if(X<=100 && Y>gameEngine.screenY-100)
                {
                    gameEngine.tower =1 ;
                }
                else if(X>= 100&& X<200 && Y>gameEngine.screenY-100)
                {
                    gameEngine.tower =2 ;
                }
                else if(X>= gameEngine.screenX-100&& X<gameEngine.screenX && Y>gameEngine.screenY-100&&gameEngine.endlevel==true){
                    gameEngine.towers.clear();
                    gameEngine.endlevel = false;
                    gameEngine.gg=false;
                    gameEngine.newGame();
                }
                //Toast.makeText(this, "ACTION_DOWN AT COORDS "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                isTouch = true;
                break;

            case MotionEvent.ACTION_MOVE:
                //Toast.makeText(this, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                break;

            case MotionEvent.ACTION_UP:
                //Toast.makeText(this, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}