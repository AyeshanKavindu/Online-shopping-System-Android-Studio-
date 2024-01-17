package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlluserReg extends AppCompatActivity {

    private Button userreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alluser_reg);

        userreg=(Button) findViewById(R.id.btnreg);

        userreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AlluserReg.this,RegisterMainActivity.class);
                startActivity(intent);


            }
        });
    }
}