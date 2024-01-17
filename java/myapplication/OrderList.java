package com.example.lfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.paperdb.Paper;

public class OrderList extends AppCompatActivity {

    private TextView OrderBack;

    private Button goFront;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        OrderBack  = (TextView) findViewById(R.id.oBackBtn);

        OrderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderList.this, Uprofile.class);
                startActivity(intent);
            }
        });

        goFront  = (Button) findViewById(R.id.goFro);

        goFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderList.this, CompletedOrders.class);
                startActivity(intent);
            }
        });

    }
}