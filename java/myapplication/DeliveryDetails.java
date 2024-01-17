package com.example.ados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ados.Model.AdminOrders;
import com.example.ados.Model.Orders;
import com.example.ados.Model.Users;
import com.google.firebase.FirebaseCommonRegistrar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class DeliveryDetails extends AppCompatActivity {

    TextView txtusername,txtphoneNumber,txtprice,txtaddress,txtdate,txttime;
    Button btnshoworder,btncompleteorder;

    DatabaseReference dborder;
    Orders orders;

    private void orderclearControl(){
        txtusername.setText("");
        txtphoneNumber.setText("");
        txtprice.setText("");
        txtaddress.setText("");

        txtdate.setText("");
        txttime.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        txtusername = findViewById(R.id.order_user_name);
        txtphoneNumber = findViewById(R.id.order_Phone_Number);
        txtprice = findViewById(R.id.order_total_price);
        txtaddress= findViewById(R.id.order_address);
        txtdate = findViewById(R.id.order_date);
        txttime = findViewById(R.id.order_time);

        btnshoworder = findViewById(R.id.show_all_products_btn);

        btncompleteorder = findViewById(R.id.complete_order);

        orders = new Orders();

        btnshoworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child("1");
                orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChildren()) {
                            txtusername.setText(dataSnapshot.child("name").getValue().toString());
                            txtphoneNumber.setText(dataSnapshot.child("phone").getValue().toString());
                            txtprice.setText(dataSnapshot.child("totalAmount").getValue().toString());
                            txtaddress.setText(dataSnapshot.child("address").getValue().toString());
                            txtdate.setText(dataSnapshot.child("date").getValue().toString());
                            txttime.setText(dataSnapshot.child("time").getValue().toString());
                       }
                        else
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });




        btncompleteorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Orders");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("1")) {
                            dborder = FirebaseDatabase.getInstance().getReference().child("Orders").child("1");
                            dborder.removeValue();
                            orderclearControl();
                            Toast.makeText(getApplicationContext(), "Order Completed", Toast.LENGTH_SHORT).show();
                        }

                        else
                            Toast.makeText(getApplicationContext(), "No Source to Delete", Toast.LENGTH_SHORT).show();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {



                    }
                });
            }
        });

    }


}