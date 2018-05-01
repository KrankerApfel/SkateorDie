package com.example.waltherp38.skateordie;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Player implements GameObject {
    private Rect rec;
    private int color;

    public Player(Rect rec, int color ){
        this.rec = rec;
        this.color = color;
    }


    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(this.color);
        canvas.drawRect(rec,p);
    }

    @Override
    public void update() {

    }

    public void update(Point pt){
        rec.set(pt.x - rec.width()/2, pt.y - rec.height()/2,pt.x +rec.width()/2,pt.y + rec.height()/2);
    }
}
