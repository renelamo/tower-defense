package com.towerint.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.towerint.Controller.GameEngine;

abstract class Printable {
    private int x;
    private int y;
    private GameEngine parent;
    private Bitmap image;
    private int resource;

    //TODO: il faudra connaitre la taille de l'image pour l'afficher centrée sur la position de l'objet. Elle s'affiche actuellement en bas à droite.
    Printable(int posX, int posY, GameEngine engine, int resource){
        x=posX;
        y=posY;
        //parent=engine;
        //this.resource=resource;
        image= BitmapFactory.decodeResource(engine.getResources(),resource);
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(image, x, y, paint);
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

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }
}
