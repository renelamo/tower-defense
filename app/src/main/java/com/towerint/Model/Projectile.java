package com.towerint;

abstract class Projectile {
    private double x;
    private double y;
    private double speed; // la vitesse à laquelle se déplace le projectile
    private double power; //private image; TODO créer une classe pour l'image
    private String projectileTouch;

    public double getX(){
        return x;
    };
    public double getY(){
        return y;
    };
    public double getSpeed(){
        return speed;
    };
    public double getPower(){
        return power;
    };
    public String getProjectileTouch() {
        return projectileTouch;
    };

    public void setX(double x) {
        this.x = x;
    };

    public void setY(double y) {
        this.y = y;
    };
}
