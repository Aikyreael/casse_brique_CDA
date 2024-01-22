package jeu.models;

import jeu.CasseBrique;

import java.awt.*;

public class Barre extends Rectangle {

    protected int vitesse = 10;

    public Barre() {
        super(
                0,
                CasseBrique.HAUTEUR - 100,
                Color.BLUE,
                200,
                20
        );

        x = CasseBrique.LARGEUR / 2 - largeur / 2;
    }

    public void deplacerGauche() {
        if(x > 0) {
            x -= vitesse;
        }
    }

    public void deplacerDroite() {
        if(x < CasseBrique.LARGEUR - largeur) {
            x += vitesse;
        }
    }
}
