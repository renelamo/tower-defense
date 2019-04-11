package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

abstract class Projectile extends Movable {
    //TODO Faire en sorte que la classe hérite proprement de Movable
    private Vector2 position;
    private double speed; // la vitesse à laquelle se déplace le projectile
    private double power;
    private static final int resource= R.drawable.kisspng_missile_weapon_computer_icons_projectile_missile_turret_5b22188ee4cce9;

    public Projectile(int posX, int posY,GameEngine parent, int resource){
        super(posX, posY, parent, resource);
    }
    public double getSpeed(){
        return speed;
    };
    public double getPower(){
        return power;
    };

    public void setSpeed(double speed){ //A utiliser pour la vitesse de tire des differentes tour
        this.speed=speed;
    }

}
