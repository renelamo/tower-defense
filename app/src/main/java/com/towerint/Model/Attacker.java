package com.towerint.Model;

import com.towerint.Controller.GameEngine;

import static com.towerint.Controller.GameEngine.FPS;

public abstract class Attacker extends Printable {
    private double radius;
    private double range;
    private float speedMoveX;
    private float speedMoveY;
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

    public void move(){
        setX(getX()+speedMoveX);
        setY(speedMoveY);
    }
    public void setSpeedMoveY(float y){
        this.speedMoveY=y/FPS;
    }

    public float getSpeedMoveX(){
        return this.speedMoveX;
    }

    public void setSpeedMoveX(float x){
        this.speedMoveX=x/FPS;
    }

    public void setSpeed(int x, int y){
        speedMoveX=x;
        speedMoveY=y;
    }
}
