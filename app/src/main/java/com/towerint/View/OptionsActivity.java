package com.towerint.View;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import com.towerint.View.MainActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.towerint.R;


public class OptionsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_options);

        musicIntent=new Intent(OptionsActivity.this, MusicService.class);
        bindService(musicIntent, musicConnection, Context.BIND_AUTO_CREATE);


        /*Déclaration des boutons*/
        final Button returnButton=findViewById(R.id.returnButton);
        final Button musicButton=findViewById(R.id.musicButton);
        /*Connecte le bouton de retour*/
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.toggle();
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbind();
        super.onDestroy();
    }

    private void unbind(){
        if(isMusicBound){
            unbindService(musicConnection);
            isMusicBound=false;
        }
    }
}