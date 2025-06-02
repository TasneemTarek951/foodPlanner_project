package com.example.foodplanner_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import Authentication.LogoutPresenter;
import Authentication.LogoutView;
import Authentication.LogoutpresenterImp;
import db.Repository;

public class MainActivity2 extends AppCompatActivity implements LogoutView {

    NavController navController;
    BottomNavigationView bottomNavigationView;
    private LogoutPresenter presenter;
    String username;
    public static String type;
    public static boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        presenter = new LogoutpresenterImp(this, this, this);

        isConnected = NetworkUtils.isConnected(this);

        // Setup Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Retrieve user data
        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.username);
        type = intent.getStringExtra(MainActivity.type);

        if (!isConnected) {
            Toast.makeText(this, "No internet connection. Some features are disabled.", Toast.LENGTH_SHORT).show();
        }

        if (type != null && type.equals("Guest")) {
            disableGuestFeatures();
        }
    }

    private void disableGuestFeatures() {
        bottomNavigationView.getMenu().findItem(R.id.favoriteFragment).setEnabled(false);
        bottomNavigationView.getMenu().findItem(R.id.myPlaneFragment).setEnabled(false);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeActivity() {
        finish();
    }

    public static class NetworkUtils {
        public static boolean isConnected(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
    }
}
