package com.towerint.Model;

public class Vector2 { //TODO: si ca ne fait pas lagger, rmplacer les float par des double
    private float x;
    private float y;

    //////CONSTRUCTEURS/////////////////////

    public Vector2(){
        x=0;
        y=0;
    }
    public Vector2(float x, float y){
        this.x=x;
        this.y=y;
    }

    public Vector2(Vector2 v){
        this.x=v.x;
        this.y=v.y;
    }

    //////SETTERS////////////////////////////

    //avec coordonnées cartésiennes
    public void setC(float x,float y){
        this.x=x;
        this.y=y;
    }

    //avec coordonnées polaires
    public void setP(float r, float theta){
        this.x=r*(float)Math.cos(theta);
        this.y=r*(float)Math.sin(theta);
    }

    //////GETTERS/////////////////////////////

    public float getTheta(){//En degrés
        return (float)Math.atan2(y,x)*180/(float)Math.PI;
    }

    public float getNorm(){
        return (float)Math.sqrt(x*x+y*y);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    //////OPERATIONS MATHEMATIQUES/////////////

    //Addition
    public Vector2 add(Vector2 v){
        float x=this.x+v.x;
        float y=this.y+v.y;
        return new Vector2(x,y);
    }

    //Produit scalaire
    public float dot(Vector2 v){
        return this.x*v.x+this.y+v.y;
    }

    //Soustraction
    public Vector2 diff(Vector2 v){
        float x=this.x-v.x;
        float y=this.y-v.y;
        return new Vector2(x,y);
    }

    //Multiplication par un scalaire
    public void mult(float f){
        x*=f;
        y*=f;
    }

    //pour que les valeurs cartésiennes du vecteur soient comprises dans l'intervalle spécifié
    public void setInBounds(Vector2 vMin, Vector2 vMax){
        if(vMax.getX()<=vMin.getX() || vMax.getY()<=vMin.getY()){
            return;
        }
        setC(Math.max(Math.min(getX(), vMax.getX()), vMin.getX()), Math.max(Math.min(getY(), vMax.getY()), vMin.getY()));
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
