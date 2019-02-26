package com.towerint;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mPlayButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        /*Déclaration des boutons*/
        final Button exitButton=findViewById(R.id.exitButton);
        final Button optionButton=findViewById(R.id.optionsButton);
        final Button startButton=findViewById(R.id.activity_main_play_btn);

        /*Définition de la fonction appelée par exitButton*/
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*Appel du layout d'options*/
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, optionsActivity.class);
                startActivity(intent);
            }
        });

        /*Appel de l'activite de jeu*/
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, gameActivity.class);
                startActivity(intent);
            }
        });
    }
}
