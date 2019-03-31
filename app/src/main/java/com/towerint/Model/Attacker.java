package com.towerint;

abstract class Attacker {
    private double x;
    private double y;
    private double radius;
    private double range;
    private double speedMove;
    private double speedAttack;
    //private image; TODO créer une classe pour l'image
    private Projectile projectile; // TODO créer classe Projectile
    private String deathSound;

    public double getX(){
        return x;
    };
    public double getY(){
        return y;
    };
    public double getRadius(){
        return radius;
    };
    public double getRange(){
        return range;
    };
    public double getSpeedMove(){
        return speedMove;
    };
    public double getSpeedAttack(){
        return speedAttack;
    };
    public Projectile getProjectile(){
        return projectile;
    };
    public String getDeathSound() { return deathSound;};

    public void setX(double x) {
        this.x = x;
    };

    public void setY(double y) {
        this.y = y;
    };
}
