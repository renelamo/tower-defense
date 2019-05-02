package com.towerint.Model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.towerint.Controller.GameEngine;
import java.util.ArrayList;
import java.util.List;

import static com.towerint.Controller.GameEngine.FPS;


public abstract class Tower extends Printable{
    protected double radius;
    protected double range;
    protected int attackCooldown; //Durée en  millisecondes entre deux tirs
    private long delayFramesLeft; //Nombre de frames restant a attendre avant le prochain tir possible
    protected double cost;
    protected Projectile projectile;
    protected GameEngine parent;


    protected List<Attacker> cibles;
    public void addCible(Attacker attacker){
        cibles.add(attacker);
    };
    public void removeCible(){
        cibles.remove(0);
    };

    //TODO prendre en compte la range
    //
    public void miseAJourCibleOptimisee(){if(cibles.get(0).getDead()){while(cibles.get(0).getDead()){removeCible();}}};

    Tower(int posX, int posY, GameEngine parentEngine, int resource){
        super(posX,posY, parentEngine, resource);
        this.parent=parentEngine;
        delayFramesLeft =0;
        this.cibles = new ArrayList<Attacker>();
    }

    //TODO: je ne pense pas que tous ces getters soient utiles...
    public double getRadius(){
        return radius;
    };
    public double getRange(){
        return range;
    };
    public double getCost(){
        return cost;
    };
    public Projectile getProjectile(){
        return projectile;
    };

    public void faceToPoint(Vector2 v){
        this.setRotation(this.getPosition().diff(v).getTheta()-90);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        if(delayFramesLeft>0){
            --delayFramesLeft;
        }
    }

    public void shoot(Attacker target){
        Way way=new Way(new Node(this.getPosition()), new Node(target.getPosition()));
        parent.projectiles.add(new ProjectileType1(way,this.parent));
        delayFramesLeft =1000*FPS/attackCooldown;
    }

    public boolean ableToShoot(){
        return delayFramesLeft==0;
    }

}
