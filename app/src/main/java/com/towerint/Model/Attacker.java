package com.towerint.Model;

import com.towerint.Controller.GameEngine;

import static com.towerint.Controller.GameEngine.FPS;

public abstract class Attacker extends Movable {
    private Vector2 speed;//Vitesse réelle en px par frame
    private float maxSpeed;//En module (en px/frame)
    private Node node; //Noeud vers lequel il se déplace
    private int health;
    private Projectile projectile; // TODO finir classe Projectile
    private GameEngine parent;


    ///////////CONSTRUCTEURS/////////////////////////////////////
    @Deprecated
    Attacker(int posX, int posY, GameEngine parent, int resource){
        super(posX, posY,parent, resource);
    }
    Attacker(Way toFollow, GameEngine parent, int resource, float maxSpeed){
        super(toFollow, parent, resource, maxSpeed);
    }

    ///////////GETTERS////////////////////////////////////////////////

    public Projectile getProjectile(){
        return projectile;
    }

    }
