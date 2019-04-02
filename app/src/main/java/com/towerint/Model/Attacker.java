package com.towerint.Model;

import com.towerint.Controller.GameEngine;

abstract class Attacker extends Printable {
    private double radius;
    private double range;
    private int speedMoveX;
    private int speedMoveY;
    private double speedAttack;
    private Projectile projectile; // TODO créer classe Projectile

    Attacker(int posX, int posY, GameEngine parent, int resource){
        super(posX, posY,parent, resource);
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
    public Projectile getProjectile(){
        return projectile;
    };

    void move(){
        setX(getX()+speedMoveX);
        setY(getY()+speedMoveY);
    }

    void setSpeed(int x, int y){
        speedMoveX=x;
        speedMoveY=y;
    }
}
