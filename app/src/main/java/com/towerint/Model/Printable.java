package com.towerint.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.towerint.Controller.GameEngine;

abstract class Printable {
    private Vector2 position;
    private double facing; //Angle (en degrés) de rotation de l'image.
    private int height;
    private int width;
    private GameEngine parent;
    private Bitmap image;
    private int resource;

    /////////CONSTRUCTEURS//////////////////////////
    Printable(int posX, int posY, GameEngine engine, int resource){
        position=new Vector2(posX, posY);
        parent=engine;
        this.resource=resource;
        facing=0;
        image= BitmapFactory.decodeResource(engine.getResources(),resource);
        height=image.getHeight();
        width=image.getWidth();
    }

    Printable(GameEngine parent, int resource){
        this.parent=parent;
        this.resource=resource;
        image= BitmapFactory.decodeResource(parent.getResources(),resource);
        height=image.getHeight();
        width=image.getWidth();
    }

    /////////////GETTERS//////////////////////////////

    public Vector2 getPosition(){
        return position;
    }

    ////////////SETTERS///////////////////////////////

    @Deprecated
    public boolean setPos(int x, int y){
        return setPos(x, y, 0);
    }

    public boolean setPos(Vector2 v){
        float x=v.getX();
        float y=v.getY();
        //TODO: il semblerait que le gameEngine ait une dimension de 0x0 en début de jeu, corriger cela
/*
        if(x<0 || x>parent.getWidth())
            return false;
        if(y<0 || y>parent.getHeight())
            return false;
            //*/
        v.setInBounds(new Vector2(0,0), new Vector2(parent.screenX, parent.screenY));
        position=v;
        return true;
    }

    @Deprecated
    public boolean setPos(int x, int y, double theta){
        setPos(new Vector2(x,y));
        setRotation(theta);
        return true;
    }

    public void setRotation(double theta){ //En degrés
        facing=theta;
    }

    /////////////AUTRES METHODES/////////////////////

    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.rotate((float)facing, position.getX(), position.getY());
        canvas.drawBitmap(image, position.getX()-width/2, position.getY()-height/2, paint);
        canvas.restore();
    }

    static public double sqr(double a) {return a*a;};

    public double ecart(Vector2 v1, Vector2 v2 ){
        float x1=v1.getX();
        float y1=v1.getY();
        float x2=v2.getX();
        float y2=v2.getY();
        return Math.sqrt(sqr(x2 - x1) + sqr(y2 - y1));
    }
}
