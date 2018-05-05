package com.example.waltherp38.skateordie;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Dog extends Obstacle implements GameObject {
    private Bitmap skin;
    private Rect rec;

    public Dog(int color, Bitmap skin){
        super(color,skin);
    }

    @Override
    public void move(float spd) {
        super.move(spd/3);
    }
}