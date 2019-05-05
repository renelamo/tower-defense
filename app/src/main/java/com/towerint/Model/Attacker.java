package com.towerint.Model;

import com.towerint.Controller.GameEngine;

import static com.towerint.Controller.GameEngine.FPS;

public abstract class Attacker extends Movable {
    private Vector2 speed;//Vitesse réelle en px par frame
    private float maxSpeed;//En module (en px/frame)
    private Node node; //Noeud vers lequel il se déplace
    protected int health = 100;
    protected int money = 100;
    private GameEngine parent;

    ///////////METHODE/////////////////////////////////////

    public void takeDamage(int damage){
        health -= damage;
    };

    //TODO reste a voir comment on détruit l'attaquant ( animations ? )

    ///////////CONSTRUCTEURS/////////////////////////////////////

    @Deprecated
    Attacker(int posX, int posY, GameEngine parent, int resource){
        super(posX, posY,parent, resource);
    }
    Attacker(Way toFollow, GameEngine parent, int resource, float maxSpeed){
        super(toFollow, parent, resource, maxSpeed);
    }

    ///////////GETTERS////////////////////////////////////////////////
    public boolean isDead(){
        return health<=0;
    }

    public int getHealth() {
        return health;
    }

    public int getMoney() {
        return money;
    }
    ///////////SETTERS////////////////////////////////////////////////

}
