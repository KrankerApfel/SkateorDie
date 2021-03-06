package com.example.waltherp38.skateordie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
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
    public boolean playerIsMovingLeft = false;
    public boolean playerIsMovingRight = false;
    public boolean gameOver;
    public long gameOverTime;
    private Point  skaterPoint;  // position du joueur
    private Road bkg;          //  image background
    private Bitmap life[];  // tableau des images pour les vies
    private int heart;           // indice de parcours du tableau life
    private Paint p;             // un paint est ce qui permet de gérer la taille et la couleur du texte.
    private Timer timer;
    private SoundPool sfx;       // pour les effets sonors
    private int hit;             // son quand on se heurte à un obstacle
    private int loose;          // son quand on perd
    private Context context;    // context
    private OrientationData orientationData;
    private long frameTime;

    /**
     * Utilise le context de la view dans laquelle l'écran de jeu est intégré
     * Le constructeur initialise tout les objets et variables utiles au jeu.
     * */
    public GameScreen(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.context = context;
        om = new ObstacleManager(context);
        this.timer = new Timer();

        Bitmap skin = BitmapFactory.decodeResource(getResources(),R.drawable.spr_skater01);
        skater = new Player(new Rect(0,0,skin.getWidth(),skin.getHeight()), Color.rgb(255,0,0),skin);
        skaterPoint = new Point(Constants.SCREEN_WIDTH/5,3*Constants.SCREEN_HEIGTH/4);

        playerIsMoving = false;
        gameOver = false;

        heart      = 0;
        life       = new Bitmap[3];
        p          = new Paint();

        // A chaque fois qu'on perd une vie on affichera une autre partie du tableau
        life[0]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart3);
        life[1]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart2);
        life[2]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart1);

        bkg        = new Road(Color.YELLOW,BitmapFactory.decodeResource(getResources(),R.drawable.road),10);

        // tout ce qui concerne la font et color
        p.setColor(Color.BLUE);
        p.setTextSize(32);

        // tout ce qui concerne les sounds fx
        sfx  = new SoundPool.Builder().setMaxStreams(10).build();
        hit = sfx.load(context, R.raw.hit, 1);
        loose = sfx.load(context, R.raw.loose, 1);

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    public void reset(){
        timer.reset();
        heart = 0;
        skaterPoint = new Point(Constants.SCREEN_WIDTH/5,3*Constants.SCREEN_HEIGTH/4);
        playerIsMoving = false;
        om = new ObstacleManager(context);
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
                if (!gameOver){
                    /*(skaterPoint.x > getX()){
                       playerIsMoving = true;
                        playerIsMovingLeft = true;
                        skaterPoint.set(skaterPoint.x+5,skaterPoint.y);
                        System.out.println("///////////////////LEFT/////////////////" + skater.getRectangle().right);
                    }
                    if(skaterPoint.x <  getX()){
                        playerIsMoving = true;
                        playerIsMovingRight = true;
                        skaterPoint.set(skaterPoint.x-5,skaterPoint.y);
                        System.out.println("///////////////////RIGHT/////////////////" + skater.getRectangle().right);
                    }*/
                }
                else if (gameOver && System.currentTimeMillis() - gameOverTime >= 1000){
                    this.reset();
                    gameOver = false;
                    orientationData.newGame();
                }break;
            case MotionEvent.ACTION_MOVE :
                if (!gameOver && playerIsMoving) {/*
                    if(skaterPoint.x > getX() && playerIsMovingLeft){
                        skaterPoint.set(skaterPoint.x+5,skaterPoint.y);
                        System.out.println("///////////////////LEFT/////////////////" + skater.getRectangle().right);
                    }
                    if(skaterPoint.x <  getX() && playerIsMovingRight){
                        skaterPoint.set(skaterPoint.x-5,skaterPoint.y);
                        System.out.println("///////////////////RIGHT/////////////////" + skater.getRectangle().right);
                    }*/
                }break;
            case MotionEvent.ACTION_UP:
                playerIsMoving = false;
                break;

        }
        return true;
    }

    public void update(){
        if(!gameOver){
            int elapsedTime = (int)(System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();
            if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null){
                float pitch = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];
                float roll = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];

                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 1000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGTH / 1000f;
                if(skaterPoint.x > 0 && skaterPoint.x < Constants.SCREEN_WIDTH)
                    skaterPoint.x -= Math.abs(xSpeed*elapsedTime)> 2 ? xSpeed*elapsedTime : 0;
                if(skaterPoint.y > Constants.SCREEN_HEIGTH/4 && skaterPoint.y < Constants.SCREEN_HEIGTH)
                skaterPoint.y -= Math.abs(ySpeed*elapsedTime)> 2 ? ySpeed*elapsedTime : 0;
            }

            if(skaterPoint.x <= 0)
                skaterPoint.x = 5;
            else if(skaterPoint.x >= Constants.SCREEN_WIDTH)
                skaterPoint.x = Constants.SCREEN_WIDTH - 5 ;

            if(skaterPoint.y <= Constants.SCREEN_HEIGTH/4)
                skaterPoint.y = Constants.SCREEN_HEIGTH/4 + 5;
            else if(skaterPoint.y >= Constants.SCREEN_HEIGTH*4/5)
                skaterPoint.y = Constants.SCREEN_HEIGTH*4/5 - 5 ;

            skater.update(skaterPoint);
            om.update();
            bkg.update();
            timer.tick();
            if(om.collide(skater)) {
                heart++;
                sfx.play(hit,1,1,0,0,1);
                if (heart >= 3){
                    gameOver = true;
                    sfx.play(loose, 1,1,0,0,1);
                    gameOverTime = System.currentTimeMillis();
                }

            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (!gameOver){
            canvas.drawColor(Color.rgb(119,208,130));
            bkg.draw(canvas);
            canvas.drawText("Score :"+timer.getScore(), 20,60,p);
            canvas.drawBitmap(life[heart], (canvas.getWidth()/2)-100,50,null);
            skater.draw(canvas);
            om.draw(canvas);
        }

        else{
            Paint pp = new Paint();
            pp.setColor(Color.rgb(255, 255, 255));
            pp.setTextSize(100);
            canvas.drawText("GAME OVER", Constants.SCREEN_WIDTH/5,Constants.SCREEN_HEIGTH/3,pp);
            canvas.drawText("Score : "+timer.getScore(), Constants.SCREEN_WIDTH/4,Constants.SCREEN_HEIGTH/2,pp);
            canvas.drawText("Time : "+timer.getLabel2(), Constants.SCREEN_WIDTH/4,Constants.SCREEN_HEIGTH*4/6,pp);
        }
    }


}