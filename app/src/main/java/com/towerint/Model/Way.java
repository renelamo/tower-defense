package com.towerint.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

import java.util.Iterator;
import java.util.LinkedList;

public class Way {
    private LinkedList<Node> path;
    private Bitmap routeBitmap;

    public Way(){
        path=new LinkedList<>();
    }

    public Way(Node... nodes){
        path=new LinkedList<>();
        for(Node n:nodes){
            this.add(n);
        }
    }

    public void add(Node n){
        if(path.size()>0){ path.getLast().setNext(n); }
        path.addLast(n);
    }

    public void add(int x, int y){
        add(new Node(x,y));
    }

    public Node getFirstNode(){
        return path.getFirst();
    }

    @Override
    public String toString() {
        String out="";
        for(Node n:path){
            out+=n.toString()+'\n';
        }
        return out;
    }

    public void draw(Canvas canvas, Paint paint){
        routeBitmap= BitmapFactory.decodeResource(GameEngine.context.getResources(), R.drawable.sand_tile);
        routeBitmap = Bitmap.createScaledBitmap(routeBitmap,150,150,false);
        for(Node n:path){
            if(n.hasNext()){
                canvas.drawLine(n.getPosition().getX(), n.getPosition().getY(), n.getNext().getPosition().getX(), n.getNext().getPosition().getY(), paint);
                int i =(int)(n.getNext().getPosition().getX()- n.getPosition().getX())/150;
                int j =(int)(n.getNext().getPosition().getY()- n.getPosition().getY())/150;
               // canvas.drawBitmap(routeBitmap, i, j, paint);
                if (i > 0)
                {
                    for(int f=0;f<i+1;f++)
                    {
                        canvas.drawBitmap(routeBitmap, n.getPosition().getX()+150*f-50, n.getNext().getPosition().getY()-50, paint);
                    }
                }
                else
                {
                    for(int f=0;f>i-1;f--)
                    {
                        canvas.drawBitmap(routeBitmap, n.getPosition().getX()+150*f-50, n.getNext().getPosition().getY()-50, paint);
                    }
                }
                if (j>0)
                {
                    for(int f=0;f<j+1;f++)
                    {
                        canvas.drawBitmap(routeBitmap, n.getPosition().getX()-50, n.getPosition().getY()+150*f-50, paint);
                    }

                }
                else
                {
                    for(int f=0;f>j-1;f--)
                    {
                        canvas.drawBitmap(routeBitmap, n.getPosition().getX()-50, n.getPosition().getY()+150*f-50, paint);
                    }
                }



            }
        }
    }
}
