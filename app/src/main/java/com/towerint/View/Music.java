package com.towerint.View;


import android.media.MediaPlayer;
import android.content.Context;

import com.towerint.R;

public class Music extends OptionsActivity{
<<<<<<< HEAD
    private MediaPlayer backgroundMusic;
    private boolean listen = true;
    private MediaPlayer touchMusic;
    private MediaPlayer bombMusic;

    //Begin music
    protected void startBackground(Context context){
=======
    MediaPlayer backgroundMusic;
    boolean listen = true;

    //Begin music
    protected void startMusic(Context context){
>>>>>>> 10047ec989a29934ce32d64a4367e9f211015b17
        backgroundMusic = MediaPlayer.create(context, R.raw.airship_thunderchild_by_otto_halmn);
        backgroundMusic.start();
        backgroundMusic.setLooping(true);
    }
<<<<<<< HEAD
    public void touchMusic(Context context){
        touchMusic = MediaPlayer.create(context, R.raw.bipp);
        touchMusic.start();
    }
    public void bombMusic(Context context){
        bombMusic= MediaPlayer.create(context, R.raw.explosion);
        bombMusic.start();
    }

    protected void stopTouch(){
        touchMusic.stop();
    }
=======

>>>>>>> 10047ec989a29934ce32d64a4367e9f211015b17
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
<<<<<<< HEAD

    public void stopBackground(){
        backgroundMusic.stop();
    }
}
=======
}
>>>>>>> 10047ec989a29934ce32d64a4367e9f211015b17
