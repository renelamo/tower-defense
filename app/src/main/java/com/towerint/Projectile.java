package com.towerint;

abstract class Projectile {
    private double x;
    private double y;
    private double speed; // la vitesse à laquelle se déplace le projectile
    private double power;
    //private image; TODO créer une classe pour l'image

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getSpeed(){
        return speed;
    }
    public double getPower(){
        return power;
    }
}
