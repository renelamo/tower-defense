package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import static com.towerint.Model.Vector2.distance;
import static com.towerint.Controller.GameEngine.FPS;

abstract public class Movable extends Printable {

    protected Vector2 speed;//Vitesse réelle en px par frame
    protected float maxSpeed;//En module (en px/frame)
    protected Node node; //Noeud vers lequel il se déplace
    protected GameEngine parent;
    protected boolean arrived=false;
    protected int thetaOffset; //Les images n'ont pas toutes la meme orientation (en degres)

    Movable(int posX, int posY, GameEngine engine, int resource) {
        super(posX, posY, engine, resource);
        parent=engine;
    }

    Movable(Way toFollow, GameEngine parent, int resource, float maxSpeed){
        this(toFollow, parent, resource, maxSpeed, 0);
    }

    Movable(Way toFollow, GameEngine parent, int resource, float maxSpeed, int angleOffset){
        super(parent, resource);
        this.parent=parent;
        this.maxSpeed=maxSpeed/FPS;
        speed=new Vector2();
        thetaOffset=angleOffset;
        this.follow(toFollow);
    }

    public void follow(Way w){
        node=w.getFirstNode().getNext();
        boolean reussite =setPos(w.getFirstNode().getPosition());
        setSpeed(w.getFirstNode().getDirection());
    }

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
    public void move(){
        setPos(this.getPosition().add(speed));
        //Si la distance entre l'attaquant et le prochain noeud est inférieure à la moitié de la distance parcourue en 1 frame, on se dirrige vers le noeud suivant;
        while (Vector2.distance(node.getPosition(), this.getPosition())<=speed.getNorm()){
            if(!node.hasNext()){
                setSpeed(0,0);
                arrived=true;
            }else {
                setPos(node.getPosition());
                setSpeed(node.getDirection());
                node = node.getNext();
            }
        }
    }

    public boolean isArrived(){
        return arrived;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    protected void speedToFace(){
        this.setRotation(speed.getTheta()+thetaOffset);
    }
}
