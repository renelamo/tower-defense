package com.towerint.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.towerint.Controller.GameEngine;

abstract class Printable {
    private Vector2 position;
    private float facing; //Angle (en degrés) de rotation de l'image.
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
        if(x<0 || x>parent.getWidth())
            return false;
        if(y<0|| y>parent.getHeight())
            return false;
        position.setC(x,y);
        return true;
    }

    @Deprecated
    public boolean setPos(int x, int y, float theta){
        setPos(new Vector2(x,y));
        setRotation(theta);
        return true;
    }

    @Deprecated //TODO: passer tous les float en double
    public void setRotation(float theta){ //En degrés
        facing=theta;
    }
    public void setRotation(double theta){
        setRotation((float)theta);
    }

    /////////////AUTRES METHODES/////////////////////

    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.rotate(facing, position.getX(), position.getY());
        canvas.drawBitmap(image, position.getX()-width/2, position.getY()-height/2, paint);
        canvas.restore();
    }
}
