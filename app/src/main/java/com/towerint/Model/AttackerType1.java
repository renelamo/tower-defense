package com.towerint.Model;

import com.towerint.Controller.GameEngine;
import com.towerint.R;

public class AttackerType1 extends Attacker{
    public AttackerType1(int posX, int posY, GameEngine engine){
        super(posX,posY, engine, R.drawable.attacker1);
    }
}
