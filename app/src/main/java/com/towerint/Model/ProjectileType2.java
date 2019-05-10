package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

public class ProjectileType2 extends Projectile {
    private final static float speed=25000;
    private final static int resource= R.drawable.projectile2;

    ProjectileType2(Way toFollow, GameEngine parent){
        super(toFollow, parent, resource, speed, 90);
        power=60;
        range=200;
    }
}
