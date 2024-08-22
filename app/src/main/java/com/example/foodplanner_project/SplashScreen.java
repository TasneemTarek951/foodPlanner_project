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


        // Delay transition to MainActivity by 20 seconds (20000 milliseconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish(); // Finish SplashScreen so the user can't return to it
            }
        }, 3000); // 20 seconds delay
    }
}