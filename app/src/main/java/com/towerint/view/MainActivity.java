package com.towerint.view;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.towerint.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MusicService music;
    private Intent musicIntent;
    private boolean isMusicBound=false;
    private ServiceConnection musicConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            music=((MusicService.MusicBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            music=null;
        }
    };

    private boolean musicState=true;
    private boolean bruitagesState=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        musicIntent=new Intent(MainActivity.this, MusicService.class);
        startService(musicIntent);
        bindService(musicIntent, musicConnection, Context.BIND_AUTO_CREATE );
        isMusicBound=true;


        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        /*Déclaration des boutons*/
        final Button exitButton = findViewById(R.id.exitButton);
        final Button optionButton = findViewById(R.id.optionsButton);
        final Button startButton = findViewById(R.id.activity_main_play_btn);
        final Button tutorialButton = findViewById(R.id.tutorialButton); ;
        final Button scoreButton = findViewById(R.id.scoreButton); ;





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
                Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
                intent.putExtra("Music State", musicState);
                intent.putExtra("bruitages", bruitagesState);
                System.out.println(musicState);
                startActivityForResult(intent, 0);
            }
        });
        /*Appel du layout du tutoriel*/
        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TutorielActivity.class);
                startActivityForResult(intent, 0);

            }
        });

        /*Appel du layout du score*/
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivityForResult(intent, 0);

            }
        });






        /*Appel de l'activite de jeu*/
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("bruitages", bruitagesState);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        musicState=data.getBooleanExtra("Music State", true);
        bruitagesState=data.getBooleanExtra("bruitages", true);
    }

    @Override
    protected void onStop() {
        if(this.isFinishing()){
            music.pause();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( ! isMusicBound) {
            startService(musicIntent);
            bindService(musicIntent, musicConnection, Context.BIND_AUTO_CREATE);
            isMusicBound = true;
        }
        if(music !=null && musicState){
            music.start();
        }
    }


    @Override
    protected void onPause() {
        //Si c'est main activity qu'on quitte...
        Context context = getApplicationContext();
        List<ActivityManager.RunningTaskInfo> taskInfo  = ((ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE))).getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                music.pause();
            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unbind();
        stopService(musicIntent);
        super.onDestroy();
    }

    private void unbind(){
        if(isMusicBound){
            unbindService(musicConnection);
            isMusicBound=false;
        }
    }
}