package com.towerint.Model;

import com.towerint.Controller.GameEngine;

import static com.towerint.Controller.GameEngine.FPS;

public abstract class Attacker extends Movable {
    private float distParcourue;
    protected int health = 100;
    protected int money = 100;
    protected double coeffSpeed = 1;
    protected int freezeDuration = 0;

    ///////////METHODE/////////////////////////////////////

    public void takeDamage(int damage){
        health -= damage;
    };

    ///////////CONSTRUCTEURS/////////////////////////////////////

    @Deprecated
    Attacker(int posX, int posY, GameEngine parent, int resource){
        super(posX, posY,parent, resource);
    }
    Attacker(Way toFollow, GameEngine parent, int resource, float maxSpeed){
        super(toFollow, parent, resource, maxSpeed);
    }

    @Override
    public void move() {
        if (freezeDuration > 0){
            freezeDuration -= 1;
            coeffSpeed = 0.2;
        }
        else{
            coeffSpeed = 1;
        }
        distParcourue+=maxSpeed*coeffSpeed;
        setPos(this.getPosition().add(speed.multToNew(coeffSpeed)));
        //Si la distance entre l'attaquant et le prochain noeud est inférieure à la moitié de la distance parcourue en 1 frame, on se dirrige vers le noeud suivant;
        while (Vector2.distance(node.getPosition(), this.getPosition())<=speed.multToNew(coeffSpeed).getNorm()) {
            if (!node.hasNext()) {
                setSpeed(0, 0);
                arrived = true;
            } else {
                setPos(node.getPosition());
                setSpeed(node.getDirection());
                node = node.getNext();
            }
        }
    }

    ///////////GETTERS////////////////////////////////////////////////
    public boolean isDead(){
        return health<=0;
    }

    public int getMoney() {
        return money;
    }

    public float getDistParcourue(){
        return  distParcourue;
    }
    ///////////SETTERS////////////////////////////////////////////////

    public void setFreezeDuration(int value){
        this.freezeDuration = value;
    }
}
