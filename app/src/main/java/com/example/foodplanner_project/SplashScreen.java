package com.example.foodplanner_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    TextView appname;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appname = findViewById(R.id.app_name);
        lottie = findViewById(R.id.Lottie);

        appname.animate().translationY(-1400).setDuration(2700).setStartDelay(0);
        lottie.animate().translationY(2000).setDuration(2000).setStartDelay(2900);

        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        },20000);

    }
}