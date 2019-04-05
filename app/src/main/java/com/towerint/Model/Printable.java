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

    Printable(int posX, int posY, GameEngine engine, int resource){
        position=new Vector2(posX, posY);
        parent=engine;
        this.resource=resource;
        facing=0;
        image= BitmapFactory.decodeResource(engine.getResources(),resource);
        height=image.getHeight();
        width=image.getWidth();
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.rotate(facing, position.getX(), position.getY());
        canvas.drawBitmap(image, position.getX()-width/2, position.getY()-height/2, paint);
        canvas.restore();
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

    public boolean setPos(Vector2 v){
        float x=v.getX();
        float y=v.getY();
        if(x<=0 || x>=parent.getWidth())
            return false;
        if(y<=0|| y>=parent.getHeight())
            return false;
        position.setC(x,y);
        return true;
    }
    public boolean setPos(int x, int y, float theta){
        setPos(new Vector2(x,y));
        rotate(theta);
        return true;
    }

    public Vector2 getPosition(){
        return position;
    }
}
