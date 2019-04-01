package com.towerint.View;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.towerint.Controller.GameEngine;

public class gameActivity extends AppCompatActivity {
    public static com.towerint.Controller.GameEngine gameEngine;

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

}