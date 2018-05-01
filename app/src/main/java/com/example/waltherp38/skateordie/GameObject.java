package com.example.waltherp38.skateordie;

import android.graphics.Canvas;

/**
* Interface pour tout les objets dessinables du jeux
* */
public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
