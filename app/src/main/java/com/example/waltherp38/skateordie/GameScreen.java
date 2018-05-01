package com.example.waltherp38.skateordie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Ecran de jeux, ici on code tout ce qui concerne les
 * intéraction dans le jeu. Une classe Player et une
 * classe Obstacle doivent être créé à la place de l'image
 * du joueur
 */

public class GameScreen extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;   // thread pour la gestion de l'affichage
    private Player skater;       // class modélisant le joueur
    private Point  skaterPoint;  // position du joueur
    private Bitmap bkg;          //  image background
    private Bitmap life[];       // tableau des images pour les vies
    private Paint p;             // un paint est ce qui permet de gérer la taille et la couleur du texte.

    /**
    * Utilise le context de la view dans laquelle l'écran de jeu est intégré
     * Le constructeur initialise tout les objets et variables utiles au jeu.
    * */
    public GameScreen(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        skater = new Player(new Rect(100,100,200,200), Color.rgb(255,0,0));
        skaterPoint = new Point(150,150);

        life       = new Bitmap[3];
        p          = new Paint();

        // A chaque fois qu'on perd une vie on affichera une autre partie du tableau
        life[0]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart3);
        life[1]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart2);
        life[2]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart1);

        // tout ce qui concerne la font et color
        p.setColor(Color.BLUE);
        p.setTextSize(32);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try
            {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
            case MotionEvent.ACTION_MOVE :
                skaterPoint.set((int) event.getX(), (int) event.getY());

        }
        return true;
    }

    public void update(){
        skater.update(skaterPoint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawText("Score :", 20,60,p);
        canvas.drawBitmap(life[0], (canvas.getWidth()/2)-100,50,null);
        skater.draw(canvas);
        
    }

}
