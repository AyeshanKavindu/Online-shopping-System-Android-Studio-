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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class activity_seller_registration extends AppCompatActivity {

    private Button SellerRegbtn;
    private EditText SellerRegname, SellerRegmobile, SellerRegemail, SellerRegpassw;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);


        SellerRegbtn = (Button) findViewById(R.id.SRegbtn);
        SellerRegname = (EditText) findViewById(R.id.SRegname);
        SellerRegmobile = (EditText) findViewById(R.id.SRegmobile);
        SellerRegemail = (EditText) findViewById(R.id.SRegemail);
        SellerRegpassw = (EditText) findViewById(R.id.SRegpassw);
        loadingBar = new ProgressDialog(this);


        SellerRegbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {SellerRegbtn();
            }

        }
        );

    }

    private void SellerRegbtn()
    {
        String name = SellerRegname.getText().toString();
        String phone = SellerRegmobile.getText().toString();
        String email = SellerRegemail.getText().toString();
        String password = SellerRegpassw.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please Write Your Name...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please Write Your Mobile Number...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Write Your Email...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Write Your Password...", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Register");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Validateemail(name, phone, email, password);
        }



    }

    private void Validateemail(String name, String phone, String email, String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Snapshot) {
                if (!(Snapshot.child("Users").child(email).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", name);
                    userdataMap.put("phone", phone);
                    userdataMap.put("email", email);
                    userdataMap.put("password", password);

                    RootRef.child("Users").child(email).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(activity_seller_registration.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(activity_seller_registration.this,SellerLogin.class);
                                        startActivity(intent);

                                    }

                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(activity_seller_registration.this, "Network Error: Please Try Again...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(activity_seller_registration.this, "This " + email + "already exists." , Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(activity_seller_registration.this, "Please try again using another email address.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(activity_seller_registration.this,MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}