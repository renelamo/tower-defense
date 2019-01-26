package towerINT;

import javafx.scene.image.Image;

/**Définit les différents types de monstres*/
enum monsterType{   //TODO: ajouter d'autres types
    zombie,
    squelette;
}

public class Monster {
    private double pv;
    private double resistance;
    private double vitesse;
    private Image img;

    Monster(monsterType t){
        switch (t){ //TODO: implémenter d'autres types
                    //TODO: mettre des valeurs cohérentes
            case zombie:
                pv=100;
                resistance=10;
                vitesse=10;
                break;
            case squelette:
                pv=50;
                resistance=20;
                vitesse=20;
                break;
        }
    }

    /**Retire des PV au monstre et renvoie true s'il est toujours vivant*/
    boolean prendDegats(double degats){
        pv-=degats/resistance;
        return pv>0;
    }

    //TODO: créer une méthode de déplacement
}
