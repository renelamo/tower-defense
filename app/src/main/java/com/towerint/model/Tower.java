package com.towerint.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.towerint.controller.GameEngine;

import java.util.ArrayList;
import java.util.List;

import static com.towerint.controller.GameEngine.FPS;


public abstract class Tower extends Printable{ ;
    protected int range ;
    protected int attackCooldown; //Durée en  millisecondes entre deux tirs
    private long delayFramesLeft; //Nombre de frames restant a attendre avant le prochain tir possible
    protected int cost;
    protected Projectile projectile;
    protected GameEngine parent;
    int posX;
    int posY;


    //TODO Systeme de cibles a tester
    protected List<Attacker> targets;
    public void addTarget(Attacker attacker){
        targets.add(attacker);
    };
    public void removeTarget(){
        targets.remove(0);
    };


    //La cible est le premier élement de la liste on ne met a jour cette liste que lorsque que le premier élement n'est plus disponible (mort ou hors portée)
    public void shoot(List<Attacker> attackers) {
        ArrayList<Attacker> onRange=new ArrayList<>();
        Attacker currentTarget = null;
        for(Attacker cible:attackers){
            if(cible.getPosition().diff(getPosition()).getNorm()<=range){
                if (currentTarget == null){
                    currentTarget = cible;
                }
                else {
                    if (cible.getDistParcourue() > currentTarget.getDistParcourue())
                        currentTarget = cible;
                }
            }
        }
        if (currentTarget == null){
            return;
        }
        shoot(currentTarget);
    }

    Tower(int posX, int posY, GameEngine parentEngine, int resource){
        this(new Vector2(posX, posY), parentEngine, resource);
    }

    public Tower(Vector2 position, GameEngine parent, int resource){
        super(position, parent, resource);
        this.parent=parent;
        delayFramesLeft =0;
        this.targets = new ArrayList<>();
    }

    //TODO: je ne pense pas que tous ces getters soient utiles...
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
        faceToPoint(target.getPosition());
        if(ableToShoot()) {
            Way way = new Way(new Node(this.getPosition()), new Node(target.getPosition()));
            if(this instanceof TowerType1) {
                parent.projectiles.add(new ProjectileType1(way, this.parent));
            }else if(this instanceof TowerType2){
                parent.projectiles.add(new ProjectileType2(way, parent));
            }
            delayFramesLeft = 1000 * FPS / attackCooldown;
        }
    }

    public boolean ableToShoot(){
        return delayFramesLeft==0;
    }

    public List<Attacker> getTargets() {
        return targets;
    }
    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
}
