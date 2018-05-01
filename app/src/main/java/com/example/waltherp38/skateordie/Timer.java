package com.example.waltherp38.skateordie;

public class Timer {
    private int h;
    private int m;
    private int s;
    private String label;

    public Timer (){
        h=m=s=0;
        label = h+":"+m+":"+s;
    }

    public void reset(){
        h=m=s=0;
        label = h+":"+m+":"+s;}

    public void tick(){
        s++;
        if(s==60) {
            s=0;
            m++;
        }
        if(m==60) {
            m=0;
            h++;
        }
        label = h+":"+m+":"+s;
    }

    public String getLabel() { return label; }
}
