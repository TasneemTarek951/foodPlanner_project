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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import db.FireService;

public class MainActivity2 extends AppCompatActivity {
    NavController navController;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    String username;
    public static String type;

    private FireService fireService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fireService = new FireService(this);

        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu_24dp_5f6368_fill0_wght400_grad0_opsz24);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.username);
        type = intent.getStringExtra(MainActivity.type);

        View headerView = navigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.name_tv);
        textView.setText(username);

        Menu menu = navigationView.getMenu();
        MenuItem item1 = menu.findItem(R.id.favoriteFragment);
        MenuItem item2 = menu.findItem(R.id.myPlaneFragment);
        MenuItem item3 = menu.findItem(R.id.log_out_item);

        updateMenuItemsBasedOnConnection();

        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                fireService.signOut(MainActivity2.this, new FireService.fireCallback() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        finish();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(MainActivity2.this, "failed signOut!", Toast.LENGTH_SHORT).show();

                    }
                });
                return true;
            }
        });

        if(type.equals("Guest")){
            item1.setEnabled(false);
            item2.setEnabled(false);
            item3.setEnabled(false);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMenuItemsBasedOnConnection();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            }else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static class NetworkUtils {
        public static boolean isConnected(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
    }

    private void updateMenuItemsBasedOnConnection() {
        Menu menu = navigationView.getMenu();
        boolean isConnected = NetworkUtils.isConnected(this);

        // Assuming the IDs of the two specific icons are nav_icon1 and nav_icon2
        MenuItem item1 = menu.findItem(R.id.homeFragment);
        MenuItem item2 = menu.findItem(R.id.searchFragment);

        if (isConnected) {
            item1.setEnabled(true);
            item2.setEnabled(true);
        } else {
            item1.setEnabled(false);
            item2.setEnabled(false);
            Toast.makeText(this, "No internet connection. Some features are disabled.", Toast.LENGTH_SHORT).show();
        }
    }
}