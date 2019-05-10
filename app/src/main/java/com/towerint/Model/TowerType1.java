package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

public class TowerType1 extends Tower {
    public static final int cost=100;
    public TowerType1(int posX, int posY, GameEngine engine){
        this(new Vector2(posX, posY), engine);
    }

    public TowerType1(Vector2 position, GameEngine engine){
        super(position, engine, R.drawable.tower1);
        attackCooldown=1000;
        super.cost=cost;
        range=500;
    }
}