package com.example.waltherp38.skateordie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class GameScreen extends View {
    private Bitmap skater; // private Player skater
    private Bitmap bkg;
    private Bitmap life[];
    private Paint p;

    public GameScreen(Context context) {
        super(context);
        life       = new Bitmap[3];
        p          = new Paint();

        skater     = BitmapFactory.decodeResource(getResources(),R.drawable.spr_skater01);
        life[0]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart3);
        life[1]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart2);
        life[2]    = BitmapFactory.decodeResource(getResources(),R.drawable.heart1);

        p.setColor(R.color.colorPrimary);
        p.setTextSize(32);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(skater,50,100,null);
        canvas.drawText("Score :", 20,60,p);
        canvas.drawBitmap(life[0], (canvas.getWidth()/2)-100,50,null);
    }
}
