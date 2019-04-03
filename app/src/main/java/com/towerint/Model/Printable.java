package com.towerint.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.towerint.Controller.GameEngine;

abstract class Printable {
    private int x;
    private int y;
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
        Matrix matrix=new Matrix(); //TODO: ce n'est pas optimisé de recalculer la nouvelle image à chaque affichage
        matrix.preRotate(facing);
        Bitmap buff=Bitmap.createBitmap(image, 0,0,width, height,matrix, true);
        int w=buff.getWidth();
        int h=buff.getHeight();
        canvas.drawBitmap(buff, x-w/2, y-h/2, paint);
    }

    public void setX(int x) {
        if(x<0)
            return;
        this.x = x;
    }

    public void setY(int y) {
        if(y<0)
            return;
        this.y = y;
    }

    public void setFacing(float theta){
        if(theta<-180 || theta>180)
            return;
        facing=theta;
    }

    public void setPos(int x, int y){
        setPos(x, y, 0);
    }

    public void setPos(int x, int y, float theta){
        setX(x);
        setY(y);
        setFacing(theta);
    }

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }
}
