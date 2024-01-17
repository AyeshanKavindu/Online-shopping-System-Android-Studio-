package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AllusersLogin extends AppCompatActivity {


    private Button userlogl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allusers_login);

        userlogl=(Button) findViewById(R.id.btnlogin);

        userlogl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AllusersLogin.this,Lodinuser.class);
                startActivity(intent);
            }
        });
    }
}