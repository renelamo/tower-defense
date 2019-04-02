package com.towerint.Model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.towerint.Controller.GameEngine;
import com.towerint.Controller.GameView;
import com.towerint.R;

public class TowerType1 extends Tower {
    public TowerType1(int posX, int posY, GameEngine engine){
        super(posX, posY, engine, R.drawable.tower);
        radius=2;
        range=10;
        speedAttack=10;
        //projectile=new ProjectileType1();
        manaMax=10;
        probabilityLooseMana=0.5;
    }
}