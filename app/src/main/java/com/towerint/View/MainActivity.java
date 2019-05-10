package com.towerint.View;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.towerint.R;

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
                startActivityForResult(intent, 0);
            }
        });

        /*Appel de l'activite de jeu*/
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        if(this.isFinishing()){
            unbind();
            stopService(musicIntent);
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        //Si c'est main activity qu'on quitte...
        Context context = getApplicationContext();
        List<ActivityManager.RunningTaskInfo> taskInfo  = ((ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE))).getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                unbind();
                stopService(musicIntent);
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