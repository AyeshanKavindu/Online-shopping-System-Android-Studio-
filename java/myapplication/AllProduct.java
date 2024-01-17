package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Prevaent.Prevalent;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.databinding.ActivityAllProductBinding;
import com.google.firebase.FirebaseOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class AllProduct extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAllProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Paper.init(this);

        setSupportActionBar(binding.appBarAllProduct.toolbar);

        binding.appBarAllProduct.toolbar.setTitle("Home");

        binding.appBarAllProduct.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer, binding.appBarAllProduct.toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        View headerview=navigationView.getHeaderView(0);
        TextView usernameTeaxtview=headerview.findViewById(R.id.progilename);


        usernameTeaxtview.setText(Prevalent.currentonlineuser.getName());
    }







    public  boolean onoptionsIteamselected(MenuItem item){
        int id=item.getItemId();

        return super.onOptionsItemSelected(item);

    }

    public boolean onNavigationItemSelected(MenuItem item){
        int id=item.getItemId();


        if(id==R.id.nav_carto)
        {
            Intent intent=new Intent(AllProduct.this,category.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_orders)
        {

        }

        else if(id==R.id.nav_settings)
        {
            Intent intent=new Intent(AllProduct.this,SettingActivity.class);
            startActivity(intent);


        }
        else if(id==R.id.nav_logout)
        {
            Toast.makeText(AllProduct.this,"logged out...",Toast.LENGTH_SHORT).show();
            Paper.book().destroy();
            Intent intent=new Intent(AllProduct.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer=(DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}