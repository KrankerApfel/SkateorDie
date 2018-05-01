package com.example.waltherp38.skateordie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private Animation uptodown, downtoup;
    private ImageView deco;

    private GameScreen gs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gs = new GameScreen(this);

        btn = (Button) findViewById(R.id.btn);
        deco = (ImageView) findViewById(R.id.deco);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        btn.setAnimation(downtoup);
        deco.setAnimation(uptodown);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(gs);
            }
        });
    }
}
