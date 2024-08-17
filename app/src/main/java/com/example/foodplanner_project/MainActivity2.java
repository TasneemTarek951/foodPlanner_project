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

import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity {
    NavController navController;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu_24dp_5f6368_fill0_wght400_grad0_opsz24);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.username);
        String type = intent.getStringExtra(MainActivity.type);

        View headerView = navigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.name_tv);
        textView.setText(username);

        Menu menu = navigationView.getMenu();
        MenuItem item1 = menu.findItem(R.id.favoriteFragment);
        MenuItem item2 = menu.findItem(R.id.myPlaneFragment);
        MenuItem item3 = menu.findItem(R.id.log_out_item);

        item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                finish();
                return true;
            }
        });

        if(type.equals("Guest")){
            item1.setEnabled(false);
            item2.setEnabled(false);
        }


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
}