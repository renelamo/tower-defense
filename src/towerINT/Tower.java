package towerINT;

import javafx.scene.image.Image;

/**Définit les différents types de tours*/
enum TowerType{ //TODO: Donner de vrais noms aux tours
    Type1,
    Type2;
}
public class Tower {
    private double portee;
    private double puissance;
    private double rapidite;
    private double cout;
    private Image img;

    Tower(TowerType t){
        switch (t){ //TODO: donner des valeurs cohérentes aux champs
            case Type1:
                portee=10;
                puissance=10;
                rapidite=10;
                cout=10;
                break;
            case Type2:
                portee=20;
                puissance=20;
                rapidite=20;
                cout=20;
                break;
        }
    }
}
