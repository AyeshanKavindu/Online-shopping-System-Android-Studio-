package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class phones extends AppCompatActivity {

    private Button btnsam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones);

        btnsam=(Button) findViewById(R.id.btnsamsung);

        btnsam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(phones.this,paymentprofile.class);
                startActivity(intent);
            }
        });
    }
}