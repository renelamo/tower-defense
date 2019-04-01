package com.towerint.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.towerint.Controller.GameView;
import com.towerint.R;

public class TowerType1 extends Tower {
    public TowerType1(GameView parent, int posX, int posY){
        super(parent, posX, posY);
        radius=2;
        range=10;
        speedAttack=10;
        image= BitmapFactory.decodeResource(parent.getResources(), R.drawable.tower);
        //projectile=new ProjectileType1();
        manaMax=10;
        probabilityLooseMana=0.5;
    }
}
