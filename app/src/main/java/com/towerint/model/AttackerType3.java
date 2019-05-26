package com.towerint.model;

import com.towerint.controller.GameEngine;
import com.towerint.R;

public class AttackerType3 extends Attacker{
    private static final int resource=R.drawable.attacker3;
    private static final int speed=14000;


    @Deprecated
    public AttackerType3(int posX, int posY, GameEngine engine){
        super(posX,posY, engine, resource);
        setSpeed(speed);
    }

    public AttackerType3(Way toFollow, GameEngine parent){
        super(toFollow, parent, resource, speed);
        money=30;
        health=100;
    }
}
