package com.example.waltherp38.skateordie;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private long startTime;



    public ObstacleManager(){
        this.obstacles = new ArrayList<Obstacle>();

        this.startTime = System.currentTimeMillis();

        generateObstacles();
    }

    private void generateObstacles(){
        for (int i = 0 ; i <5 ; i++) {
            obstacles.add(new Obstacle(Color.rgb(0,55,200)));
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

        /*if (obstacle.getRectangle().left < 0){
            this.spawnY = (int) (Math.random() * (Constants.SCREEN_WIDTH - 100)) + 100;
            obstacles.get(obstacles.size() - 1).set(spawnX, spawnY,spawnX+100,spawnY+100);
            System.out.println("respawn at : " + spawnY);
        }*/
    }
    public void draw(Canvas canvas){
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }
    }
}
