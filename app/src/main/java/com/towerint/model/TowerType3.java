package com.towerint.model;

import com.towerint.controller.GameEngine;
import com.towerint.R;

import java.util.List;

public class TowerType3 extends Tower {
    public static final int cost=250;
    public TowerType3(int posX, int posY, GameEngine engine){
        this(new Vector2(posX, posY), engine);
    }

    public TowerType3(Vector2 position, GameEngine engine){
        super(position, engine, R.drawable.tower3);
        attackCooldown=1;
        super.cost=cost;
        range=200;
    }

    @Override
    public void shoot(List<Attacker> attackers) {
        for(Attacker cible:attackers){
            if(cible.getPosition().diff(getPosition()).getNorm()<=range){
                cible.setFreezeDuration(100);
            }
        }
    }
}