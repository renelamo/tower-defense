package com.towerint.Model;

import com.towerint.Controller.GameEngine;

import static com.towerint.Controller.GameEngine.FPS;

public abstract class Attacker extends Printable {
    private double radius;
    private double range;
    private Vector2 speed;// en px par frame
    private float maxSpeed;
    private Node node;
    private double attackCooldown;
    private int health;
    private Projectile projectile; // TODO créer classe Projectile

    Attacker(int posX, int posY, GameEngine parent, int resource){
        super(posX, posY,parent, resource);
        speed=new Vector2();
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
        setPos(speed.add(getPosition()));
        if(node.getPosition().diff(this.getPosition()).getNorm()<maxSpeed){ //Si la distance entre l'attaquant et le prochain noeud est inférieure à la distance parcourue en 1 frame, on se dirrige vers le noeud suivant;
            if(!node.hasNext()){
                //TODO: destruction base
            }
            setSpeed(node.getDirection());
            node = node.getNext();
        }
    }

    public void setSpeed(int x, int y){ //En px/s
        speed.setC(x/FPS,y/FPS);
        speedToFace();
    }

    public void setSpeed(Vector2 v){
        float n=v.getNorm()*maxSpeed;
        setSpeed((int)(v.getX()*n),(int)(v.getY()*n));
    }

    public void setSpeed(float s){
        if(speed.getNorm()!=0) {
            speed.mult(s / speed.getNorm());
        }
        maxSpeed=s/FPS;
    }

    public void speedToFace(){
        rotate(speed.getTheta());
    }

    public void follow(Way w){
        node=w.getFirstNode();
    }
}
