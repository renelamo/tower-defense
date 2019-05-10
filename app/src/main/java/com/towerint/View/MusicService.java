package com.towerint.View;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.towerint.R;

public class MusicService extends Service {
    private MediaPlayer player;

    // Attribut de type IBinder
    private final IBinder mBinder = new MusicBinder();

    // Le Binder est représenté par une classe interne
    class MusicBinder extends Binder {
        // Le Binder possède une méthode pour renvoyer le Service
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(player==null) {
            player = MediaPlayer.create(this, R.raw.airship_thunderchild_by_otto_halmn);
            player.setVolume(100, 100);
            player.setLooping(true);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!player.isPlaying()) {
            player.start();
        }
        return START_NOT_STICKY; //On ne relance pas automatiquement le service pas en cas d'erreur
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void pause(){
        player.pause();
    }

    public void toggle(){
        if(player.isPlaying()){
            player.pause();
        }else{
            player.start();
        }
    }

    public boolean isPlaying(){
        return player.isPlaying();
    }

    public void start(){
        player.start();
    }

}
