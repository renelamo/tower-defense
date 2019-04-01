package com.towerint.Model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.towerint.Controller.GameView;
import com.towerint.R;

public class TowerType1 extends Tower {
    //private GameView gameView;
    public TowerType1(int posX, int posY, GameView gameView){
        super(posX, posY);
        radius=2;
        range=10;
        speedAttack=10;
        image= BitmapFactory.decodeResource(gameView.getResources(), R.drawable.png_exemple);
        //projectile=new ProjectileType1();
        manaMax=10;
        probabilityLooseMana=0.5;
    }
}
