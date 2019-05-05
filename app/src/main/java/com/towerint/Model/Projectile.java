package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

abstract public class Projectile extends Movable {
    private Vector2 position;
    //private float speed; // la vitesse à laquelle se déplace le projectile
    private int power = 30;
    private int range=100;

    public Projectile(int posX, int posY,GameEngine parent, int resource){
        super(posX, posY, parent, resource);
    }
    Projectile(Way toFollow, GameEngine parent,int resource, float speed, int angleOffset){
        super(toFollow, parent, resource, speed, angleOffset);
        thetaOffset=90;
    }

    public int getPower(){ return power; }

    public int getRange(){ return this.range;}
}
