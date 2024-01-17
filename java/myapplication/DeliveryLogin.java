package com.example.ados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ados.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeliveryLogin extends AppCompatActivity {

    private Button Dloginbutton,Dsignupbtn;
    private EditText DRPhoneNumber,DRpw;
    private ProgressDialog loadingbar;

    private String DparentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Dloginbutton = (Button) findViewById(R.id.Dloginbutton);
        Dsignupbtn = (Button) findViewById(R.id.Dsignupbtn);
        DRpw = (EditText) findViewById(R.id.DRpw);
        DRPhoneNumber = (EditText) findViewById(R.id.protel);
        loadingbar = new ProgressDialog(this);

        Dloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DLoginUser();
            }
        });


        Dsignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeliveryLogin.this,DeliveryRegister.class);
                startActivity(intent);
            }
        });

    }

    private void DLoginUser() {
        String PhoneNumber = DRPhoneNumber.getText().toString();
        String Password = DRpw.getText().toString();

        if(TextUtils.isEmpty(PhoneNumber)){
            Toast.makeText(this,"please write your Phone Number..",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Password)){
            Toast.makeText(this,"please write your password..",Toast.LENGTH_SHORT).show();
        }

        else{
            loadingbar.setTitle("Login Delivery Account");
            loadingbar.setMessage("Please wait,while we are checking the credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            AccessToLoginAccount(PhoneNumber,Password);
        }
    }

    private void AccessToLoginAccount(String PhoneNumber, String Password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.child(DparentDbName).child(PhoneNumber).exists()) {
                   Users usersData = snapshot.child(DparentDbName).child(PhoneNumber).getValue(Users.class);

                   if(usersData.getPhoneNumber().equals(PhoneNumber))
                   {
                       if(usersData.getPassword().equals(Password))
                       {
                           Toast.makeText(DeliveryLogin.this, "Login Successfully..", Toast.LENGTH_SHORT).show();
                           loadingbar.dismiss();

                           Intent intent = new Intent(DeliveryLogin.this,DeliveryHome.class);
                           startActivity(intent);
                       }
                   }

               }
               else
               {
                   Toast.makeText(DeliveryLogin.this, "Account with this "+ PhoneNumber + "number do not exsist",
                           Toast.LENGTH_SHORT).show();
                   loadingbar.dismiss();

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}