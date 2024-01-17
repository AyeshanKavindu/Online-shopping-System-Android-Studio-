package com.example.lfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class register extends AppCompatActivity
{
    private Button regButton;
    private EditText rName,rMail,rAddress,rPhone,rPassword;
    private ProgressDialog loadingBar;

    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textView1 = (TextView) findViewById(R.id.text_view1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this,dHome.class);
                startActivity(intent);
                Toast.makeText(register.this, "Login Here", Toast.LENGTH_LONG).show();
            }
        });


        rName = (EditText) findViewById(R.id.rName);
        rMail = (EditText) findViewById(R.id.rMail);
        rAddress = (EditText) findViewById(R.id.rAddress);
        rPhone = (EditText) findViewById(R.id.rPhone);
        rPassword = (EditText) findViewById(R.id.rPassword);
        regButton = (Button) findViewById(R.id.regButton);
        loadingBar = new ProgressDialog(this);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount();
            }
        });


    }

    private void CreateAccount()
    {
        String name = rName.getText().toString();
        String email = rMail.getText().toString();
        String address = rAddress.getText().toString();
        String telephone = rPhone.getText().toString();
        String password = rPassword.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter A User Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter A Email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Please Enter A Address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(telephone)){
            Toast.makeText(this, "Please Enter A Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter A Password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, While we are checking the credential");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail(name,email,address,telephone,password);
        }
    }

    private void ValidateEmail(String name, String email, String address, String telephone, String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if (!(snapshot.child("Users").child(telephone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<String, Object>();
                    userdataMap.put("name", name);
                    userdataMap.put("email", email);
                    userdataMap.put("address", address);
                    userdataMap.put("telephone", telephone);
                    userdataMap.put("password", password);

                    RootRef.child("Users").child(telephone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(register.this, "Your Account has been created", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(register.this,dHome.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        loadingBar.dismiss();
                                        Toast.makeText(register.this, "Network Error,Please try again!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(register.this, "This "+telephone+" Already Exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(register.this, "Please try again using new email ..", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(register.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}