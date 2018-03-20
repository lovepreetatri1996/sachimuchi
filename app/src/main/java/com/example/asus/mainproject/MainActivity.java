package com.example.asus.mainproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView volun = findViewById(R.id.volunteer);
        Animation alpha = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
        volun.setAnimation(alpha);
        alpha.start();
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,FirstPage.class);
                startActivity(i);

            }
        };
        h.postDelayed(r, 7000);
    }
}
