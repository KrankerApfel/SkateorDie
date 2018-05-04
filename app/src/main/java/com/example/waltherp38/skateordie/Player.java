package com.example.waltherp38.skateordie;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Player implements GameObject {
    private Rect rec;
    private int color;
    private Bitmap skin;

    public Player(Rect rec, int color, Bitmap skin ){
        this.rec = rec;
        this.color = color;
        this.skin = skin;
    }

    @Override
    public Rect getRectangle() { return this.rec; }

    @Override
    public void draw(Canvas canvas) {
        /* hitbox*/
        Paint p = new Paint();
        p.setColor(this.color);
        canvas.drawRect(rec,p);
        canvas.drawBitmap(this.skin,rec.left,rec.top,null);
    }

    @Override
    public void update() {

    }

    public void update(Point pt){
        rec.set(pt.x - rec.width()/2, pt.y - rec.height()/2,pt.x +rec.width()/2,pt.y + rec.height()/2);
    }
}
