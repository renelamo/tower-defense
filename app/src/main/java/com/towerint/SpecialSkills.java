package com.towerint;

public class SpecialSkills {
    private double x;
    private double y;
    private double radius;
    private double popularityCost;
    //private specialEffect specialEffect;
    //TODO definir la classe effet special et décider si ca peut changer temporairement les stats des tours/attaquants dans une zone donnée
    //TODO comme ca on met un setteur sur les parametres des ces tours/attaquants

    public double getX(){
        return x;
    };
    public double getY(){
        return y;
    };
    public double getRadius(){
        return radius;
    };
    public double getPopularityCost(){
        return popularityCost;
    };
    //public specialEffect getSpecialEffect (){
    //    return specialEffect;
    //};

    public void setX(double x) {
        this.x = x;
    };
    public void setY(double y) {
        this.y = y;
    };
}
