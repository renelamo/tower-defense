package com.towerint.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.towerint.Controller.GameView;
import com.towerint.R;

public class TowerType1 extends Tower {
    TowerType1(GameView parent, int posX, int posY){
        super(parent, posX, posY);
        radius=2;
        range=10;
        speedAttack=10;
        //image= BitmapFactory.decodeResource(parent.getResources(), R.anim.);
        //projectile=new ProjectileType1();
    }
}
