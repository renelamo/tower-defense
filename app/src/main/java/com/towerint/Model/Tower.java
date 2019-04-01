package com.towerint.Model;

import android.graphics.Bitmap;

import com.towerint.Controller.GameView;

abstract class Tower {
    private double x;
    private double y;
    protected double radius;
    protected double range;
    protected double speedAttack;
    protected double cost;
    protected Bitmap image;
    private GameView parent;
    protected Projectile projectile;
    protected double manaMax;
    protected double ProbabilityLooseMana;
    // TODO Eventuellement variable qui définit quelles sont les cibles de la tour

    Tower(GameView parent, int posX, int posY){
        x=posX;
        y=posY;
        this.parent=parent;
    }

    //TODO: je ne pense pas que tous ces getters soient utiles...
    public double getX(){
        return x;
    };
    public double getY(){
        return y;
    }
    public double getRadius(){
        return radius;
    };
    public double getRange(){
        return range;
    };
    public double getSpeedAttack(){
        return speedAttack;
    };
    public double getCost(){
        return cost;
    };
    public Projectile getProjectile(){
        return projectile;
    };

    public void setX(double x) {
        this.x = x;
    };

    public void setY(double y) {
        this.y = y;
    };
}
