package com.towerint.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

abstract class Printable {
    private int x;
    private int y;
    protected Bitmap image;

    Printable(int posX, int posY){
        x=posX;
        y=posY;
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(image, x, y, paint);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
