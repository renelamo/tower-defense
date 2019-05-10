package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

public class AttackerType1 extends Attacker{
    private static final int resource=R.drawable.attacker1;
    private static final int speed=10000;



    //TODO: définir les autres constantes pour ce type d'attaquant et les implémenter dans le constructeur

    @Deprecated
    public AttackerType1(int posX, int posY, GameEngine engine){
        super(posX,posY, engine, resource);
        setSpeed(speed);
    }

    public AttackerType1(Way toFollow, GameEngine parent){
        super(toFollow, parent, resource, speed);
        money=20;
        health=200;
    }
}
