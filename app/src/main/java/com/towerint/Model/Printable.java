package com.towerint.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.towerint.Controller.GameEngine;

abstract class Printable {
    private float x;
    private float y;
    private float facing; //Angle (en degrés) de rotation de l'image.
    private int height;
    private int width;
    private GameEngine parent;
    private Bitmap image;
    private int resource;

    Printable(int posX, int posY, GameEngine engine, int resource){
        x=posX;
        y=posY;
        parent=engine;
        this.resource=resource;
        facing=0;
        image= BitmapFactory.decodeResource(engine.getResources(),resource);
        height=image.getHeight();
        width=image.getWidth();
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.rotate(facing, x, y);
        canvas.drawBitmap(image, x-width/2, y-height/2, paint);
        canvas.restore();
    }

    public boolean setX(float x) {
        if(x<=0 || x>=parent.getWidth())
            return false;
        this.x = x;
        return true;
    }

    public boolean setY(float y) {
        if(y<=0|| y>=parent.getHeight())
            return false;
        this.y = y;
        return true;
    }

    public void rotate(float theta){//Prend une valeur entre -180 et +180 degrés
        facing=theta;
    }
    public void rotate(double theta){
        rotate((float)theta);
    }

    public boolean setPos(int x, int y){
        return setPos(x, y, 0);
    }

    public boolean setPos(int x, int y, float theta){
        rotate(theta);
        return setX(x) && setY(y);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
