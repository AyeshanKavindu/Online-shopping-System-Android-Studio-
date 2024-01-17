package com.example.ados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button SelprofileButton, SelprodCategButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SelprofileButton = (Button) findViewById(R.id.SProfilebtn);
        SelprodCategButton = (Button) findViewById(R.id.Saddprbtn);

        SelprofileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,Sprofile.class);
                startActivity(intent);
            }
        });

        SelprodCategButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,SellerProdCatg.class);
                startActivity(intent);
            }
        });

    }
}