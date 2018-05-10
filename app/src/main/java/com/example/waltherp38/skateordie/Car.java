package com.example.waltherp38.skateordie;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Car extends Obstacle implements GameObject {
    private Rect rec; // hitbox
    private int color; // couleur de l'hitbox
    private int spawnX; // abcsisse du point d'apparition
    private int spawnY; // ordonne du point d'apparition
    private Bitmap skin; // image

    /**
     * Constructeur pour un obstacle avec image
     * */
    public Car(int color, Bitmap skin){
        super(color,skin);
        this.spawnX = (int) (Math.random() * (4*Constants.SCREEN_HEIGTH - (2*Constants.SCREEN_HEIGTH))) + (2*Constants.SCREEN_HEIGTH);
        this.spawnY = (int) (Math.random() * ((Constants.SCREEN_WIDTH/2.8) - 150)) + 150; // int random = (int)(Math.random() * (higher-lower)) + lower ==> [lower,higher[
        this.skin   = skin;
        this.rec    = new Rect(spawnX, spawnY+skin.getHeight()/2,spawnX+skin.getWidth(),spawnY+skin.getHeight());
        this.color  = color;

    }



    @Override
    /**
     * Check la collision avec le joueur
     * */
    public boolean collide(Player player){
        if(Rect.intersects(this.rec,player.getRectangle())){
            spawnY = (int)(Math.random() * ((Constants.SCREEN_WIDTH/2.8) - 150)) + 150;
            this.rec    = new Rect(spawnX, spawnY+skin.getHeight()/2,spawnX+skin.getWidth(),spawnY+skin.getHeight());
            return true;
        }

        return false;
    }


    @Override
    /**
     * Fait avancer l'obstacle
     * */
    public void move(float spd ){
        this.rec.left  -= spd;
        this.rec.right -= spd;

        if(this.rec.right < 0){
            spawnY    = (int)(((Math.random() * ((Constants.SCREEN_WIDTH/2.8) - 150)) + 150)/30)*30;
            this.rec  = new Rect(spawnX, spawnY+skin.getHeight()/2,spawnX+skin.getWidth(),spawnY+skin.getHeight());
        }
    }



    @Override
    /**
     * Dessine sur le canvas
     **/
    public void draw(Canvas canvas) {
        /* draw hit box
        Paint p = new Paint();
        p.setColor(this.color);
        canvas.drawRect(rec,p);*/
        canvas.drawBitmap(this.skin,rec.left,rec.top - skin.getHeight()/2,null);

    }

    @Override
    public void update() {

    }
}