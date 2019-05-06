package com.towerint.Model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.towerint.Controller.GameEngine;
import java.util.ArrayList;
import java.util.List;

import static com.towerint.Controller.GameEngine.FPS;


public abstract class Tower extends Printable{
    protected double radius;
    protected int range ;
    protected int attackCooldown; //Durée en  millisecondes entre deux tirs
    private long delayFramesLeft; //Nombre de frames restant a attendre avant le prochain tir possible
    protected int cost;
    protected Projectile projectile;
    protected GameEngine parent;


    //TODO Systeme de cibles a tester
    protected List<Attacker> targets;
    public void addTarget(Attacker attacker){
        targets.add(attacker);
    };
    public void removeTarget(){
        targets.remove(0);
    };


    //La cible est le premier élement de la liste on ne met a jour cette liste que lorsque que le premier élement n'est plus disponible (mort ou hors portée)
    public void towerTargetsUpdate(List<Attacker> attackers) {
        if(targets.isEmpty() &(! attackers.isEmpty())){
            for(Attacker cur: attackers) {
                if (ecart(cur.getPosition(), this.getPosition()) <= this.range) {
                    addTarget(cur);
                }
            }
        }
        else{
            //FIXME: il y a une boucle infinie là
            while (!targets.isEmpty()){
                if(targets.get(0).isDead() ) {
                    removeTarget();
                }
                else if((ecart(targets.get(0).getPosition(), this.getPosition()) > this.range)){
                    removeTarget();
                }
            };
        };
    };

    Tower(int posX, int posY, GameEngine parentEngine, int resource){
        super(posX,posY, parentEngine, resource);
        this.parent=parentEngine;
        delayFramesLeft =0;
        this.targets = new ArrayList<Attacker>();
    }

    //TODO: je ne pense pas que tous ces getters soient utiles...
    public double getRadius(){
        return radius;
    };
    public double getRange(){
        return range;
    };
    public int getCost(){
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

    public List<Attacker> getTargets() {
        return targets;
    }
}
