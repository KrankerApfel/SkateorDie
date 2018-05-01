package com.example.waltherp38.skateordie;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject{
    private Rect rec;
    private int color;
    private int spawnX;
    private int spawnY;


    public Obstacle(Rect rec, int color){
        this.rec = rec;
        this.color = color;
        //2*Constants.SCREEN_HEIGTH;
        this.spawnX = (int) (Math.random() * (4*Constants.SCREEN_HEIGTH - (2*Constants.SCREEN_HEIGTH))) + (2*Constants.SCREEN_HEIGTH);
    }

    public Obstacle(int color){
        this.spawnX = (int) (Math.random() * (4*Constants.SCREEN_HEIGTH - (2*Constants.SCREEN_HEIGTH))) + (2*Constants.SCREEN_HEIGTH);
        this.spawnY = (int) (Math.random() * ((Constants.SCREEN_WIDTH/2.8) - 150)) + 150; // int random = (int)(Math.random() * (higher-lower)) + lower ==> [lower,higher[
        this.rec = new Rect(spawnX, spawnY,spawnX+100,spawnY+100);
        this.color = color;
    }

    public boolean collide(Player player){
        if(Rect.intersects(this.rec,player.getRectangle())){
            spawnY = (int)(Math.random() * ((Constants.SCREEN_WIDTH/2.8) - 150)) + 150;
            this.set(spawnX, spawnY,spawnX+100,spawnY+100);
            return true;
        }

        return false;
    }

    public void move(float spd ){
        this.rec.left  -= spd;
        this.rec.right -= spd;

        if(this.rec.right < 0){
            spawnY = (int)(Math.random() * ((Constants.SCREEN_WIDTH/2.8) - 150)) + 150;
            this.set(spawnX, spawnY,spawnX+100,spawnY+100);
        }
    }

    public void set(int l, int t, int r, int b){
        this.rec.set(l,t,r,b);
    }
    @Override
    public Rect getRectangle() { return this.rec; }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(this.color);
        canvas.drawRect(rec,p);
    }

    @Override
    public void update() {

    }
}
