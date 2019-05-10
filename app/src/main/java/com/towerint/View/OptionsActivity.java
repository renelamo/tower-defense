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
import android.widget.Switch;

import com.towerint.R;


public class OptionsActivity extends AppCompatActivity {

    private boolean musicState;
    private boolean bruitagesState;
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
        isMusicBound=true;

        Intent parentIntent=getIntent();

        musicState=parentIntent.getBooleanExtra("Music State", true);
        bruitagesState=parentIntent.getBooleanExtra("bruitages", true);

        /*Déclaration des boutons*/
        final Button returnButton=findViewById(R.id.returnButton);
        final Switch musicButton=findViewById(R.id.musicButton);
        final Switch bruitagesSwitch=findViewById(R.id.bruitages_button);
        /*Connecte le bouton de retour*/
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult();
                unbind();
                finish();
            }
        });

        musicButton.setChecked(musicState);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.toggle();
                musicState= !musicState;
            }
        });

        bruitagesSwitch.setChecked(bruitagesState);
        bruitagesSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bruitagesState= !bruitagesState;
            }
        });
    }

    @Override
    public void onBackPressed() {
        sendResult();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        unbind();
        sendResult();
        super.onDestroy();

    }

    private void unbind(){
        if(isMusicBound){
            unbindService(musicConnection);
            isMusicBound=false;
        }
    }

    private void sendResult(){
        Intent out=new Intent();
        out.putExtra("Music State", musicState);
        out.putExtra("bruitages", bruitagesState);
        setResult(RESULT_OK, out);
    }
}