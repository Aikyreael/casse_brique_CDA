package jeu;

import jeu.models.*;
import jeu.models.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CasseBrique extends Canvas implements KeyListener, MouseListener{

    public static final int LARGEUR = 500;
    public static final int HAUTEUR = 600;
    public Boolean pause = false;
    protected ArrayList<Balle> listeBalle = new ArrayList<>();
    protected ArrayList<Brique> listeBrique = new ArrayList<>();
    protected ArrayList<Bonus> listeBonus = new ArrayList<>();
    protected Barre barre = new Barre();
    protected Bouton pauseBouton = new Bouton((int)(LARGEUR * 0.15),(int)(HAUTEUR - 35),Color.GRAY,(int)(LARGEUR * 0.3),20);
    protected Bouton rebootBouton = new Bouton((int)(LARGEUR * 0.55),(int)(HAUTEUR - 35),Color.GRAY,(int)(LARGEUR * 0.3),20);


    public CasseBrique() {
        JFrame fenetre = new JFrame();

        this.setSize(LARGEUR, HAUTEUR );
        this.setBounds(0,0, LARGEUR, HAUTEUR);
        this.setIgnoreRepaint(true);
        this.setFocusable(false);

        fenetre.pack();
        fenetre.setSize(LARGEUR , HAUTEUR+30);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.setResizable(false);
        fenetre.requestFocus();
        fenetre.addKeyListener(this);
        this.addMouseListener(this);

        Container panneau = fenetre.getContentPane();
        panneau.add(this);


        fenetre.setVisible(true);
        this.createBufferStrategy(2);
        init();
        demarrer();
    }

    public void init() {
        for(int i = 0 ; i < 3 ; i++) {
            listeBalle.add(new Balle(20));
        }
        for(int i = 0 ; i < 10 ; i++) {
            for(int j = 0 ; j < 10 ; j++) {
                listeBrique.add(new Brique(i * CasseBrique.LARGEUR / 10 - 10,
                        j * 20,
                        new Color((float)Math.random(),(float)Math.random(),(float)Math.random()),
                        CasseBrique.LARGEUR / 11,
                        15));
            }
        }
    }

    public void demarrer() {
        while(true) {
                try {
                    Graphics2D dessin = (Graphics2D) this.getBufferStrategy().getDrawGraphics();


                    if(!pause) {
                        dessin.setColor(Color.WHITE);
                        dessin.fillRect(0, 0, LARGEUR, HAUTEUR );

                        barre.dessiner(dessin);
                        pauseBouton.dessiner(dessin);
                        rebootBouton.dessiner(dessin);

                        ArrayList<Brique> copyListeBrique = new ArrayList<>(listeBrique);
                        ArrayList<Bonus> copyListeBonus = new ArrayList<>(listeBonus);
                        ArrayList<Balle> copyListeBalle = new ArrayList<>(listeBalle);
                        for (Balle balle : copyListeBalle) {
                            balle.dessiner(dessin);
                            balle.deplacement();
                            if (balle.getY() + balle.getDiametre() > HAUTEUR - 50) {
                                listeBalle.remove(balle);
                            }
                            if (barre.collision(balle)) {
                                balle.setVitesseVertical(-balle.getVitesseVertical());
                            }

                            for (Brique brique : copyListeBrique) {
                                if (brique.collision(balle)) {
                                    listeBrique.remove(brique);
                                    balle.setVitesseVertical(-balle.getVitesseVertical());
                                    if (Math.random() > 0.80) {
                                        listeBonus.add(new Bonus(brique.getX(), brique.getY(), 5));
                                    }
                                }
                            }
                        }
                        for (Bonus bonus : copyListeBonus) {
                            bonus.dessiner(dessin);
                            bonus.deplacementBonus();
                            if (barre.collision(bonus)) {
                                System.out.println("Bonus !");
                                listeBonus.remove(bonus);
                                barre.setLargeur(barre.getLargeur() + 10);
                                barre.setX(barre.getX() - 5);
                            }
                            if (bonus.getY() + bonus.getDiametre() > HAUTEUR - 50) {
                                listeBonus.remove(bonus);
                            }
                        }

                        for (Brique brique : listeBrique) {
                            brique.dessiner(dessin);
                        }
                    }

                    dessin.dispose();
                    this.getBufferStrategy().show();

                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    System.out.println("processus arreté");
                }
            }
        }
    public void reset() {
        listeBalle.clear();
        listeBonus.clear();
        listeBrique.clear();
        barre.setLargeur(200);
        barre.setX(CasseBrique.LARGEUR / 2 - barre.getLargeur() / 2);
        init();

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //quand la touche gauche est enfoncée
        if(e.getKeyCode() == 37) {
            barre.deplacerGauche();
        } else if (e.getKeyCode() == 39) {
            //quand la touche droite est enfoncée
            barre.deplacerDroite();
        }else if (e.getKeyCode() == 38) {
            //quand la touche haut est enfoncée
            reset();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (e.getX() > pauseBouton.getX()
                && e.getX() < pauseBouton.getX() + pauseBouton.getLargeur()
                && e.getY() > pauseBouton.getY()
                && e.getY() < pauseBouton.getY() + pauseBouton.getHauteur()) {
            pause = !pause;
        } else if(e.getX() > rebootBouton.getX()
                && e.getX() < rebootBouton.getX() + rebootBouton.getLargeur()
                && e.getY() > rebootBouton.getY()
                && e.getY() < rebootBouton.getY() + rebootBouton.getHauteur()) {
            reset();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        new CasseBrique();
    }

}
