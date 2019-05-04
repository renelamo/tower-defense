package com.towerint.Model;

import com.towerint.Controller.GameEngine;

import static com.towerint.Controller.GameEngine.FPS;

public abstract class Attacker extends Movable {
    private Vector2 speed;//Vitesse réelle en px par frame
    private float maxSpeed;//En module (en px/frame)
    private Node node; //Noeud vers lequel il se déplace
    private int health;
    private Projectile projectile;
    private GameEngine parent;
    private boolean dead;


    ///////////METHODE/////////////////////////////////////

    public void takeDamage(int damage){this.health = this.health - damage;};

    void isDead(){if(this.health <= 0){ dead = true;}
    else dead = false;};
    //TODO reste a voir comment on détruit l'attaquant ( animations ? ) et quand on sait que le projectile a touché

    ///////////CONSTRUCTEURS/////////////////////////////////////


    // TODO mettre dead a false au début
    @Deprecated
    Attacker(int posX, int posY, GameEngine parent, int resource){
        super(posX, posY,parent, resource);
    }
    Attacker(Way toFollow, GameEngine parent, int resource, float maxSpeed){
        super(toFollow, parent, resource, maxSpeed);
    }

    ///////////GETTERS////////////////////////////////////////////////
    public boolean getDead(){
        return dead;
    }

    public Projectile getProjectile(){
        return projectile;
    }

    }
