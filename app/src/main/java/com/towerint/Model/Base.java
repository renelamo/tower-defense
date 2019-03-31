package com.towerint;

public class Base {
    private double x;
    private double y;
    private double lifePoints;
    private String destroySound;

    public double getX(){
        return x;
    };
    public double getY(){
        return y;
    };
    public double getLifePoints(){
        return lifePoints;
    };
    public double getDestroySound(){
        return destroySound;
    };

    public void setX(double x) {
        this.x = x;
    };
    public void setY(double y) {
        this.y = y;
    };
    public void setLifePoints(double x) {
        this.lifePoints = lifePoints;
    };
}
