package com.example.mobileapps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import android.view.MenuItem;


import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {

    BottomNavigationView nav;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button todolistbtn = findViewById(R.id.buttonToDoList);

        todolistbtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, ToDoListActivity.class);
            startActivity(intent);
        });

        Button shoppingitem = findViewById(R.id.buttonShopping);

        shoppingitem.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this,ShoppingPageActivity.class);
            startActivity(intent);
        });




        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        nav=findViewById(R.id.bottom_navigation);

        nav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_home){
                    return true;
                } else if (id == R.id.action_profile) {
                    startActivity(new Intent(HomePageActivity.this,ProfilePageActivity.class));
                    return true;
                } else if (id == R.id.action_settings) {
                    startActivity(new Intent(HomePageActivity.this,SettingActivity.class));
                    return true;
                }
                return false;
            }
        });

//        Button btnMenu = findViewById(R.id.btnMenu);
//        btnMenu.setVisibility(View.GONE);

//        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

//        navigationView.setNavigationItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.nav_profile) {
//                Intent intent = new Intent(HomePageActivity.this, ProfilePageActivity.class);
//                startActivity(intent);
//                drawerLayout.closeDrawer(GravityCompat.START);
//                return true;
//            }
//            return false;

//        });




    }
}