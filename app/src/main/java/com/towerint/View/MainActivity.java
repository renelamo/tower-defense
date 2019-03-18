package com.towerint.View;

import android.content.Intent;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.media.AudioManager;

import com.towerint.R;

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

        /*
        SoundPool.Builder builder=new SoundPool.Builder();
        SoundPool soundManager=builder.build();
        */
        SoundPool soundManager= new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        int nyanCat=soundManager.load(getApplicationContext(),R.raw.nyancat,1);
        soundManager.play(nyanCat, 1, 1, 1, -1, 1);

    }
}
