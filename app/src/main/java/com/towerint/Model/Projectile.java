package com.towerint.Model;

import com.towerint.Controller.GameEngine;

abstract class Projectile extends Printable {
    private Vector2 position;
    private double speed; // la vitesse à laquelle se déplace le projectile
    private double power;
    //private image; TODO créer une classe pour l'image

    public Projectile(GameEngine parent, int resource){
        super(parent, resource);
    }
    public double getSpeed(){
        return speed;
    };
    public double getPower(){
        return power;
    };

}
