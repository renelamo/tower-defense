package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

public class TowerType1 extends Tower {
    public TowerType1(int posX, int posY, GameEngine engine){
        super(posX, posY, engine, R.drawable.tower1);
        radius=2;
        range=10;
        speedAttack=10;
        //projectile=new ProjectileType1();
    }
}