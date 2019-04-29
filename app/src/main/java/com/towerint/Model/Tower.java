package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import java.util.ArrayList;
import java.util.List;


public abstract class Tower extends Printable{
    protected double radius;
    protected double range;
    protected int attackCooldown;
    private long nextTimeFire;
    protected double cost;
    protected Projectile projectile;
    protected GameEngine parent;
    // TODO Eventuellement variable qui définit quelles sont les cibles de la tour

    protected List<Attacker> cibles;
    public void addcible(Attacker attacker){
            cibles.add(attacker);
    };





    Tower(int posX, int posY, GameEngine parentEngine, int resource){
        super(posX,posY, parentEngine, resource);
        this.parent=parentEngine;
        nextTimeFire=0;
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

    public void shoot(Attacker target){
        Way way=new Way(new Node(this.getPosition()), new Node(target.getPosition()));
        parent.projectiles.add(new ProjectileType1(way,this.parent));
        nextTimeFire=attackCooldown+System.currentTimeMillis();
    }

    public long getNextTimeFire(){
        return nextTimeFire;
    }

}
