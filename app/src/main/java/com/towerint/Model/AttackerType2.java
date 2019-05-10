package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

public class AttackerType2 extends Attacker{
    private static final int resource=R.drawable.attacker2;
    private static final int speed=7000;



    //TODO: définir les autres constantes pour ce type d'attaquant et les implémenter dans le constructeur

    @Deprecated
    public AttackerType2(int posX, int posY, GameEngine engine){
        super(posX,posY, engine, resource);
        setSpeed(speed);
    }

    public AttackerType2(Way toFollow, GameEngine parent){
        super(toFollow, parent, resource, speed);
        money=40;
        health=400;
    }
}
