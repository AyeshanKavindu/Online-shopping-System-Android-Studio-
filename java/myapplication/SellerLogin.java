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

public class SellerLogin extends AppCompatActivity {
    private EditText Emailfielddd, Passwordfield;
    private Button Loginbutton, RegisterButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        Emailfielddd = (EditText) findViewById(R.id.Emailfield);
        Passwordfield = (EditText) findViewById(R.id.Pwfield);
        Loginbutton = (Button) findViewById(R.id.Loginbtn);
        RegisterButton = (Button) findViewById(R.id.SReg);
        loadingBar = new ProgressDialog(this);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerLogin.this,activity_seller_registration.class);
                startActivity(intent);
            }
        });


        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginUser();
            }
        });
    }


    private void LoginUser() {

        String email = Emailfielddd.getText().toString();
        String password = Passwordfield.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Write Your Email...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Write Your Password...", Toast.LENGTH_SHORT).show();
        }

        else{
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(email,password);
        }
    }

    private void AllowAccessToAccount(String email, String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if(snapshot.child(parentDbName).child(email).exists())
                 {
                     Users usersData = snapshot.child(parentDbName).child(email).getValue(Users.class);

                     if(usersData.getEmail().equals(email))
                     {
                         if(usersData.getPassword().equals(password))
                         {
                             Toast.makeText(SellerLogin.this, "Logged in Successfully...", Toast.LENGTH_SHORT).show();
                             loadingBar.dismiss();

                             Intent intent = new Intent(SellerLogin.this,HomeActivity.class);
                             startActivity(intent);
                         }
                     }

                 }
                 else{
                     Toast.makeText(SellerLogin.this, "Account with this" + email + "Email is not valid", Toast.LENGTH_SHORT).show();
                     loadingBar.dismiss();

                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }
}