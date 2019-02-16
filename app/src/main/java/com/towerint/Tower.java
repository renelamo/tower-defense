package com.towerint;

abstract class Tower {
    private double x;
    private double y;
    private double radius;
    private double range;
    private double speedAttack;
    private double cost;
    //private image; TODO créer une classe pour l'image
    private projectile projectile;
    private double popularity_max;
    private double ProbabilityLoosePopularity;
    // TODO Eventuellement variable qui définit quelles sont les cibles de la tour


    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getRadius(){
        return radius;
    }
    public double getRange(){
        return range;
    }
    public double getSpeedAttack(){
        return speedAttack;
    }
    public double getCost(){
        return cost;
    }
    public projectile getProjectile(){
        return projectile;
    }

}
