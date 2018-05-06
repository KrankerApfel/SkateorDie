package com.example.waltherp38.skateordie;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Road implements GameObject {
    private Rect rec;
    private Rect rec2;
    private int color;
    private Bitmap skin;
    private int speed;

    public Road( int color, Bitmap skin, int spd ){
        this.color  = color;
        this.color = color;
        this.skin = skin;
        this.rec = new Rect(0, Constants.SCREEN_HEIGTH - skin.getHeight(),skin.getWidth(),skin.getHeight());
        this.rec2 = new Rect(rec.right, Constants.SCREEN_HEIGTH - skin.getHeight(),rec.right+skin.getWidth(),skin.getHeight());
        this.speed = spd;
    }
    @Override
    public void draw(Canvas canvas) {
        /* hitbox*/
        Paint p = new Paint();
        p.setColor(this.color);
        canvas.drawRect(rec,p);
        canvas.drawBitmap(this.skin,rec.left,rec.top,null);
        canvas.drawBitmap(this.skin,rec2.left,rec2.top,null);

    }

    @Override
    public void update() {
        this.rec.left  -= speed;
        this.rec.right -= speed;
        this.rec2.left  -= speed;
        this.rec2.right -= speed;

        if(this.rec.right < 0){
            this.rec.set(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGTH - skin.getHeight(),Constants.SCREEN_WIDTH+skin.getWidth(),skin.getHeight());
        }
        if(this.rec2.right < 0){
            this.rec2.set(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGTH - skin.getHeight(),Constants.SCREEN_WIDTH+skin.getWidth(),skin.getHeight());
        }
    }

    @Override
    public Rect getRectangle() {return this.rec;}
}
