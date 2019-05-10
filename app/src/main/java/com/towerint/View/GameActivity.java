package com.towerint.View;

import com.towerint.Model.Vector2;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import com.towerint.Model.Tower;
import com.towerint.Controller.GameEngine;
import com.towerint.Model.TowerType1;
import com.towerint.Model.TowerType2;
import com.towerint.R;


public class GameActivity extends AppCompatActivity {
    public com.towerint.Controller.GameEngine gameEngine;
    public boolean isTouch = false;
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
        int eventAction = event.getAction();
        int partX=(int)(gameEngine.screenX*.15);
        if(eventAction==MotionEvent.ACTION_DOWN) {
            if(X>gameEngine.screenX-partX && Y<partX){//Bouton de pause
                if(paused)
                {
                    onResume();
                }else {
                    onPause();
                }
                gameEngine.begin=true;
            }
            else if(X<=partX && Y>gameEngine.screenY-partX) //CHoix tour type 1
            {
                gameEngine.tower =1 ;
            }
            else if(X>= partX&& X<2*partX && Y>gameEngine.screenY-partX) //choix tour tyoe 2
            {
                gameEngine.tower =2 ;
            }
            else if(X>= 2*partX&& X<3*partX && Y>gameEngine.screenY-partX){// Bouton start
                gameEngine.begin=true;
            }
            else if(X>= 3*partX&& X<4*partX && Y>gameEngine.screenY-partX) { //Bouton Menu
                finish();
            }
            else if(X>= gameEngine.screenX-partX&& X<gameEngine.screenX && Y>gameEngine.screenY-partX&&gameEngine.endlevel==true) { //Bouton restart
                gameEngine.towers.clear();
                gameEngine.endlevel = false;
                gameEngine.gg = false;
                gameEngine.begin = false;
                gameEngine.newGame();
            }else{
                createTower(new Vector2(X, Y));
            }
            //Toast.makeText(this, "ACTION_DOWN AT COORDS "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
            isTouch = true;
        }
        return true;
    }

    private boolean createTower(Vector2 position){
        int partX=(int)(gameEngine.screenX*.15);
        if(gameEngine.getPath().isOnPath(position)){
            System.out.println("Tour sur le chemin");
            return false;
        }
        for(Tower t:gameEngine.towers){
            if(Vector2.distance(t.getPosition(), position)< partX){
                System.out.println("Tour sur Tour");
                return false;
            }
        }
        switch (gameEngine.tower){
            case 1:
                if(gameEngine.money<TowerType1.cost){
                    return false;
                }
                MediaPlayer construction=MediaPlayer.create(gameEngine.getContext(), R.raw.construction);
                construction.setLooping(false);
                construction.start();
                gameEngine.towers.add(new TowerType1(position, gameEngine));
                gameEngine.money-=TowerType1.cost;
                break;
            case 2:
                if(gameEngine.money<TowerType2.cost){
                    return false;
                }
                gameEngine.towers.add(new TowerType2(position, gameEngine));
                gameEngine.money-=TowerType2.cost;
                break;
        }
        return true;
    }

}