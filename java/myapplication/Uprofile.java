package com.example.lfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class Uprofile extends AppCompatActivity {

    private Button logoutBtn,showProfile,orderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uprofile);

        logoutBtn  = (Button) findViewById(R.id.logout_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();

                Intent intent = new Intent(Uprofile.this, dHome.class);
                startActivity(intent);
            }
        });


        showProfile = (Button) findViewById(R.id.uPro);

        showProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Uprofile.this, NewProfile.class);
                startActivity(intent);
            }
        });

        orderBtn = (Button) findViewById(R.id.orderListBtn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Uprofile.this, OrderList.class);
                startActivity(intent);
            }
        });





    }
}