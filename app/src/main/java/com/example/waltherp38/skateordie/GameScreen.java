package com.example.waltherp38.skateordie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Ecran de jeux, ici on code tout ce qui concerne les
 * intéraction dans le jeu. Une classe Player et une
 * classe Obstacle doivent être créé à la place de l'image
 * du joueur
 */

public class GameScreen extends View {
    private Bitmap skater; // à remplacer par :  private Player skater où Player est une classe.
    private Bitmap bkg;    // background.
    private Bitmap life[]; // tableau de trois image représentant les vies.
    private Paint p;       // un paint est ce qui permet de gérer la font et couleur du texte.

    public GameScreen(Context context) { // récupère le context de la view dans laquelle il est intégré
        super(context);
        life       = new Bitmap[3];
        p          = new Paint();

        skater     = BitmapFactory.decodeResource(getResources(),R.drawable.spr_skater01); // remplacer par l'instanciation d'un objet Player
        // A chaque fois qu'on perd une vie on affichera une autre partie du tableau
        life[0]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart3);
        life[1]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart2);
        life[2]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart1);

        // tout ce qui concerne la font et color
        p.setColor(R.color.colorPrimary);
        p.setTextSize(32);

    }

    @Override
    // Dans cette fonction on affiche tout ce qui doit se dessiner à l'écran
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(skater,50,100,null);
        canvas.drawText("Score :", 20,60,p);
        canvas.drawBitmap(life[0], (canvas.getWidth()/2)-100,50,null);
    }
}
