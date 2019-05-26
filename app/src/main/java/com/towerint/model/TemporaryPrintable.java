package com.towerint.model;

import com.towerint.controller.GameEngine;


public class TemporaryPrintable extends Printable {

    private long timeToLive;
    private long timeCreated;

    public TemporaryPrintable(int posX, int posY, GameEngine engine, int resource, int timeToLive) {
        super(posX, posY, engine, resource);
        this.timeToLive=timeToLive;
        this.timeCreated = System.currentTimeMillis();
    }
    public TemporaryPrintable(Vector2 pos, GameEngine engine, int resource, int timeToLive) {
        this(Math.round(pos.getX()), Math.round(pos.getY()), engine, resource, timeToLive);
    }

    public boolean isAlive(){
        return System.currentTimeMillis()-timeCreated <= this.timeToLive;
    }

}
