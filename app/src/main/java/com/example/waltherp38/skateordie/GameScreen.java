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
    private ObstacleManager om;
    private Player skater;       // class modélisant le joueur
    public boolean playerIsMoving;
    public boolean gameOver;
    public long gameOverTime;
    private Rect r;
    private Point  skaterPoint;  // position du joueur
    private Bitmap bkg;          //  image background
    private Bitmap life[];       // tableau des images pour les vies
    private Paint p;             // un paint est ce qui permet de gérer la taille et la couleur du texte.
    private Timer timer;

    /**
    * Utilise le context de la view dans laquelle l'écran de jeu est intégré
     * Le constructeur initialise tout les objets et variables utiles au jeu.
    * */
    public GameScreen(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        om = new ObstacleManager();
        this.timer = new Timer();

        skater = new Player(new Rect(100,100,200,200), Color.rgb(255,0,0));
        skaterPoint = new Point(Constants.SCREEN_WIDTH/5,3*Constants.SCREEN_HEIGTH/4);
        playerIsMoving = false;
        gameOver = false;

        life       = new Bitmap[3];
        p          = new Paint();

        // A chaque fois qu'on perd une vie on affichera une autre partie du tableau
        life[0]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart3);
        life[1]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart2);
        life[2]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart1);

        bkg        = BitmapFactory.decodeResource(getResources(),R.drawable.road);

        // tout ce qui concerne la font et color
        p.setColor(Color.BLUE);
        p.setTextSize(32);

    }

    public void reset(){
        timer.reset();
        skaterPoint = new Point(Constants.SCREEN_WIDTH/5,3*Constants.SCREEN_HEIGTH/4);
        playerIsMoving = false;
        om = new ObstacleManager();
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
                if (!gameOver && skater.getRectangle().contains((int) event.getX(), (int) event.getY())){
                    playerIsMoving = true;
                }
                else if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000){
                    this.reset();
                    gameOver = false;
                }break;
            case MotionEvent.ACTION_MOVE :
                if (!gameOver && playerIsMoving) {
                    skaterPoint.set((int) event.getX(), (int) event.getY());
                }break;
            case MotionEvent.ACTION_UP:
                playerIsMoving = false;
                break;

        }
        return true;
    }

    public void update(){
        if(!gameOver){
            skater.update(skaterPoint);
            om.update();
            timer.tick();
            if(om.collide(skater)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (!gameOver){
            canvas.drawColor(Color.rgb(119,208,130));
            canvas.drawBitmap(bkg, 0, canvas.getHeight() - bkg.getHeight(),null);
            canvas.drawText("Time :"+timer.getLabel(), 20,60,p);
            canvas.drawBitmap(life[0], (canvas.getWidth()/2)-100,50,null);
            om.draw(canvas);
            skater.draw(canvas);
        }

       else{
            Paint pp = new Paint();
            pp.setColor(Color.rgb(255, 255, 255));
            pp.setTextSize(100);
            canvas.drawText("GAME OVER", Constants.SCREEN_WIDTH/5,Constants.SCREEN_HEIGTH/3,pp);
            canvas.drawText("Time : "+timer.getLabel(), Constants.SCREEN_WIDTH/4,Constants.SCREEN_HEIGTH/2,pp);
        }
    }


}
