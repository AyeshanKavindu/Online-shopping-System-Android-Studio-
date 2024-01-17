package com.example.ados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeliveryHome extends AppCompatActivity {

    private Button DProbtn,ordersbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_home);

        DProbtn = (Button) findViewById(R.id.DProbtn);
        ordersbtn = (Button) findViewById(R.id.ordersbtn);

        DProbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryHome.this,DeliveryProfile.class);
                startActivity(intent);

            }
        });
        ordersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryHome.this,DeliveryDetails.class);
                startActivity(intent);


            }
        });
    }
}