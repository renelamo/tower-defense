package com.towerint.Model;

import com.towerint.Controller.GameEngine;

abstract class Projectile extends Printable {
    private double speedX;
    private double speedY;
    private double power;

    Projectile(int posX, int posY, GameEngine engine, int resource) {
        super(posX, posY, engine, resource);
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY(){
        return speedY;
    }


    public double getPower() {
        return power;
    }

}
