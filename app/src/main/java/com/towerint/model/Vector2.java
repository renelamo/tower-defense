package com.towerint.model;

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

    public double getTheta(){//En degrés
        return Math.atan2(y,x)*180/Math.PI;
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
        float X=this.x*v.x;
        float Y=this.y*v.y;
        return X+Y;
    }

    //Soustraction
    public Vector2 diff(Vector2 v){
        float x=this.x-v.x;
        float y=this.y-v.y;
        return new Vector2(x,y);
    }

    //Multiplication par un scalaire
    public Vector2 mult(double d){
        this.x*=d;
        this.y*=d;
        return this;
    }

    public Vector2 multToNew(double d){
        float x=(float)(this.x*d);
        float y=(float)(this.y*d);
        return new Vector2(x,y);
    }

    //pour que les valeurs cartésiennes du vecteur soient comprises dans l'intervalle spécifié
    public void setInBounds(Vector2 vMin, Vector2 vMax){
        if(vMax.getX()<=vMin.getX() || vMax.getY()<=vMin.getY()){
            return;
        }
        setC(Math.max(Math.min(getX(), vMax.getX()), vMin.getX()), Math.max(Math.min(getY(), vMax.getY()), vMin.getY()));
    }

    public float distanceDroite(Vector2 point1, Vector2 point2){
        Vector2 directeurDroite=point2.diff(point1);
        directeurDroite.mult(1/directeurDroite.getNorm()); //Vecteur normé
        Vector2 projeteSurDroite=directeurDroite.mult(directeurDroite.dot(this.diff(point1)));
        return distance(this, projeteSurDroite.add(point1));
    }

    public float distanceSegment(Vector2 pointA, Vector2 pointB){
        Vector2 AB=pointB.diff(pointA);
        Vector2 AM=this.diff(pointA);
        Vector2 BM=this.diff(pointB);
        float AMdotAB=AM.dot(AB);
        float BMdotAB=BM.dot(AB);
        if(AMdotAB<=0){
            System.out.println("distance A");
            return distance(this, pointA);
        }
        if(BMdotAB>=0){
            System.out.println("distance B");
            return distance(this, pointB);
        }
        System.out.println("distance droite");
        return distanceDroite(pointA, pointB);
    }
    public static float distance(Vector2 pos1, Vector2 pos2){
        return pos1.diff(pos2).getNorm();
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
