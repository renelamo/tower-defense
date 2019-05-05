package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

public class TowerType2 extends Tower {
    public TowerType2(int posX, int posY, GameEngine engine){
        super(posX, posY, engine, R.drawable.tower2);
        radius=2;
        attackCooldown=1000;
        cost=100;
        range=500;
        //projectile=new ProjectileType1();
    }
}