package com.towerint.View;


import android.media.MediaPlayer;
import android.content.Context;

import com.towerint.R;

public class Music extends OptionsActivity{
    MediaPlayer backgroundMusic;
    boolean listen = true;

    //Begin music
    protected void startMusic(Context context){
        backgroundMusic = MediaPlayer.create(context, R.raw.airship_thunderchild_by_otto_halmn);
        backgroundMusic.start();
        backgroundMusic.setLooping(true);
    }

    //Play or Stop
    protected void musicState() {
        if(listen) {
            backgroundMusic.pause();
            listen = false;
        }
        else{
            backgroundMusic.start();
            listen = true;
        }
    }
}
