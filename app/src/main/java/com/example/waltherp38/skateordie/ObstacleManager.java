package com.example.waltherp38.skateordie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private long startTime;
    private Context ctx;


    public ObstacleManager(Context ctx){
        this.obstacles = new ArrayList<Obstacle>();
        this.ctx = ctx;

        this.startTime = System.currentTimeMillis();

        generateObstacles();
    }

    private void generateObstacles(){
        for (int i = 0 ; i <5 ; i++) {
            Bitmap skin = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.car);
            obstacles.add(new Obstacle(Color.rgb(0,55,200),skin));
        }
    }

    public boolean collide(Player player){
        for (Obstacle obstacle: obstacles) {
            if (obstacle.collide(player)) return true;
        }
        return false;
    }

    public void update(){
        long elapsedTime = (int) (System.currentTimeMillis()- startTime);
        startTime = System.currentTimeMillis();
        float speed = Constants.SCREEN_HEIGTH/1950.0f;
        for (Obstacle obstacle : obstacles) {
            obstacle.move(speed*elapsedTime);
        }

    }
    public void draw(Canvas canvas){
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }
    }
}
