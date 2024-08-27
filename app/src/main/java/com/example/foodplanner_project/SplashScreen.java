package com.example.foodplanner_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    TextView appname;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appname = findViewById(R.id.app_name);
        lottie = findViewById(R.id.Lottie);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentUser != null) {
                    // User is logged in, navigate to the main features activity
                    Intent intent = new Intent(SplashScreen.this, MainActivity2.class);
                    startActivity(intent);
                } else {
                    // User is not logged in, navigate to the authentication activity
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }

                // Finish the splash screen activity so it's not in the back stack
                finish();
            }

        }, 3000); // 20 seconds delay
    }
}