package com.towerint;

abstract class PlayingField {
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private Way way;
    private double weigthWay;

    PlayingField(Way way){
        xMin=0;
        yMin=0;
        xMax=100;
        yMax=50;
        weigthWay=5;
        this.way=way;
    }
}
