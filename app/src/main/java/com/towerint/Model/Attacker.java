package com.towerint.Model;

import com.towerint.Controller.GameEngine;

import static com.towerint.Controller.GameEngine.FPS;

public abstract class Attacker extends Printable {
    private double radius;
    private double range;
    private Vector2 speed;//Vitesse réelle en px par frame
    private float maxSpeed;//En module (en px/frame)
    private Node node; //Noeud vers lequel il se déplace
    private double attackCooldown;
    private int health;
    private Projectile projectile; // TODO créer classe Projectile
    private GameEngine parent;


    ///////////CONSTRUCTEURS/////////////////////////////////////
    @Deprecated
    Attacker(int posX, int posY, GameEngine parent, int resource){
        super(posX, posY,parent, resource);
        this.parent=parent;
        speed=new Vector2();
    }
    Attacker(Way toFollow, GameEngine parent, int resource){
        super(parent, resource);
        follow(toFollow);
    }

    ///////////GETTERS////////////////////////////////////////////////
    public double getRadius(){
        return radius;
    }
    public double getRange(){
        return range;
    }
    public double getAttackCooldown(){
        return attackCooldown;
    }
    public Projectile getProjectile(){
        return projectile;
    }

    ///////////SETTERS///////////////////////////////////////////////

    public void setSpeed(float x, float y){ //En px/s
        speed.setC(x/FPS,y/FPS);
        speedToFace();
    }

    public void setSpeed(Vector2 v){
        float n=maxSpeed/v.getNorm();
        setSpeed(v.getX()*n,v.getY()*n);
    }

    public void setSpeed(float s){//en px/s
        s/=FPS;//en px/frame
        if(speed.getNorm()!=0) {
            speed.mult(s / speed.getNorm()/*en px/frame*/);
        }
        maxSpeed=s;
    }

    ///////////////AUTRES METHODES///////////////////////////

    public void move(){
        setPos(speed.add(this.getPosition()));
        //Si la distance entre l'attaquant et le prochain noeud est inférieure à la moitié de la distance parcourue en 1 frame, on se dirrige vers le noeud suivant;
        if(node.getPosition().diff(this.getPosition()).getNorm()<speed.getNorm()/2){ //TODO: je ne sais pas pourquoi speed.getNorm() est différent (environ 25 fois inférieur) de maxSpeed()
            if(!node.hasNext()){
                parent.score="Fin du chemin";
                setSpeed(0,0);
            }else {
                setSpeed(node.getDirection());
                node = node.getNext();
            }
        }
    }

    public void speedToFace(){
        this.setRotation(speed.getTheta());
    }

    public void follow(Way w){
        node=w.getFirstNode().getNext();
        boolean reussite =setPos(w.getFirstNode().getPosition());
        setSpeed(w.getFirstNode().getDirection());
    }
}
