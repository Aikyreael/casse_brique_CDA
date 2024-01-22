package jeu.models;

import jeu.CasseBrique;

import java.awt.*;

public class Bonus extends Balle{


    public Bonus(int x, int y, int diametre) {
        super(x, y, diametre);
        this.couleur = Color.ORANGE;
    }

    public void deplacementBonus() {
        int vitesseVertical = 5;
        y += vitesseVertical;
    }
}
