package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

public class TowerType2 extends Tower {
    public final static int cost=400;
    public TowerType2(int posX, int posY, GameEngine engine){
        this(new Vector2(posX, posY), engine);
    }

    public TowerType2(Vector2 position, GameEngine engine){
        super(position, engine, R.drawable.tower2);
        attackCooldown=1000;
        super.cost=cost;
        range=500;
    }
}