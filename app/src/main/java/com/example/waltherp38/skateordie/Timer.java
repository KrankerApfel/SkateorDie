package com.example.waltherp38.skateordie;

public class Timer {
    private int h;
    private int m;
    private int s;
    private int score;
    private String label;
    private String label2;

    public Timer (){
        h=m=s=0;
        label = h+":"+m+":"+s;
        label2 = m+"'"+s+"\"";
    }

    public void reset(){
        h=m=s=0;
        label = h+":"+m+":"+s;
        score = 0;
        label2 = m+"'"+s+"\"";
    }

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
        label2 = m+"' "+s+"\"";
        score += s/10;
    }

    public String getLabel() { return label; }
    public String getLabel2() { return label2; }
    public int getScore(){return score/100;}
}
