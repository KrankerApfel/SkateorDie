package com.example.waltherp38.skateordie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 *  Activité principale, ici on code la page d'acceuille,
 *  mais aussi tout les changements de view (il y a deux view,
 *  l'écran d'acceuille et l'écran de jeu).
 */
public class MainActivity extends AppCompatActivity {
    private Button btn;
    private Animation uptodown, downtoup;
    private ImageView deco;

    private GameScreen gs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // sur la view on affiche cette activité

        gs = new GameScreen(this);      // C'est la view du jeu, on récupère le context de la view


        // Placement des boutton et animation
        btn  = (Button) findViewById(R.id.btn);
        deco = (ImageView) findViewById(R.id.deco);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        btn.setAnimation(downtoup);
        deco.setAnimation(uptodown);

        // Un listener pour détecter si on clic sur le bouton
        // si oui alors on affiche la view du jeu
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(gs);
            }
        });
    }
}
