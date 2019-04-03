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
        canvas.drawBitmap(image, x-width/2, y-height/2, paint);
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

    public boolean rotate(float theta){
        if(theta<-180 || theta>180)
            return false;
        facing=theta;
        Matrix matrix=new Matrix();
        matrix.preRotate(facing);
        image=Bitmap.createBitmap(image, 0,0,width, height,matrix, true);
        width=image.getWidth();
        height=image.getHeight();
        return true;
    }

    public boolean setPos(int x, int y){
        return setPos(x, y, 0);
    }

    public boolean setPos(int x, int y, float theta){
        return setX(x) && setY(y) && rotate(theta);
    }

    float getX(){
        return x;
    }

    float getY(){
        return y;
    }
}
