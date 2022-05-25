package com.c323FinalProject.ctaddeuc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private TextView nav_header_name_id, nav_header_email_id;
    private String email, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nav_header_layout);
        nav_header_email_id = findViewById(R.id.nav_header_email_id);
        nav_header_name_id = findViewById(R.id.nav_header_name_id);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        nav_header_email_id.setText(email);
        nav_header_name_id.setText(name);

        setContentView(R.layout.activity_navigation_drawer);

        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        frameLayout = findViewById(R.id.framelayout_id);
        navigationView = findViewById(R.id.navigationview_id);
        navigationView.setNavigationItemSelectedListener(this);



        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if(savedInstanceState == null){
            MenuItem menuItem = navigationView.getMenu().getItem(0).setChecked(true);
            onNavigationItemSelected(menuItem);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_categories_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new CategoriesFragment()).commit();
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.nav_favorites_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new FavoritesFragment()).commit();
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;

            case R.id.nav_trash_id:
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_id, new TrashFragment()).commit();
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;


        }

        return true;
    }
}