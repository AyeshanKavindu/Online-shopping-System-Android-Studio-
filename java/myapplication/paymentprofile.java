package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.razorpay.Checkout;

public class paymentprofile extends AppCompatActivity {

    private Button paymenton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentprofile);

        paymenton=(Button) findViewById(R.id.btnonline);


        paymenton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(paymentprofile.this,creditcard.class);
                startActivity(intent);
            }
        });
    }
}