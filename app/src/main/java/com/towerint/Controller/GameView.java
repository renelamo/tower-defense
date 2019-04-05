package com.towerint.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import com.towerint.View.GameActivity;


public class GameView extends View {

    Bitmap bitmap;
    Paint paint;
    int screenX;
    int screenY;


    public GameView(Context context) {
        super(context);
       // bitmap = Bitmap.createBitmap(largeur,hauteur, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10);

        float left = 100;
        float top = 100;
        float right = 800;
        float bottom = 1200;

        canvas.drawLine(left, top, right, bottom, paint);

        // Fill the screen with color
        canvas.drawColor(Color.argb(200, 40, 200, 60));

        // Scale the HUD text
        paint.setTextSize(60);
        canvas.drawText("Score :" + GameActivity.gameEngine.score, 20, 100, paint);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
        super.onDraw(canvas);
    }
}
