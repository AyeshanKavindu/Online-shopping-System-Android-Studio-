package com.example.ados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SellerProdCatg extends AppCompatActivity {

    private ImageView mobile, cloth, sport, watch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_prod_catg);

        mobile = (ImageView) findViewById(R.id.Smobile);
        cloth = (ImageView) findViewById(R.id.Scloth);
        sport = (ImageView) findViewById(R.id.Ssport);
        watch = (ImageView) findViewById(R.id.Swatch);


        mobile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProdCatg.this,SelAddProduct.class);
                intent.putExtra("category", "mobile");
                startActivity(intent);
            }
        });

        cloth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProdCatg.this,SelAddProduct.class);
                intent.putExtra("category", "cloth");
                startActivity(intent);
            }
        });

        sport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProdCatg.this,SelAddProduct.class);
                intent.putExtra("category", "sport");
                startActivity(intent);
            }
        });

        watch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProdCatg.this,SelAddProduct.class);
                intent.putExtra("category", "watch");
                startActivity(intent);
            }
        });


    }
}









