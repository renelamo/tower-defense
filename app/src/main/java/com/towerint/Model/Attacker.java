package com.towerint.Model;

import com.towerint.Controller.GameEngine;

import static com.towerint.Controller.GameEngine.FPS;

public abstract class Attacker extends Printable {
    private double radius;
    private double range;
    private float speedMoveX;
    private float speedMoveY;
    private double attackCooldown;
    private int health;
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
    public double getAttackCooldown(){
        return attackCooldown;
    };
    public Projectile getProjectile(){
        return projectile;
    };

    public void move(){
        if(!setX(getX()+speedMoveX)) {
            speedMoveX = -speedMoveX;
            speedToFace();
        }
        if(!setY(getY()+speedMoveY)) {
            speedMoveY = -speedMoveY;
            speedToFace();
        }
    }

    public void setSpeed(int x, int y){ //En px/s
        speedMoveX=x/(float)FPS;
        speedMoveY=y/(float)FPS;
        speedToFace();
    }

    public void speedToFace(){
        rotate(Math.atan2(speedMoveY, speedMoveX)*180/Math.PI);
    }
}
