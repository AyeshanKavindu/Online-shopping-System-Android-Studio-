package com.example.myapplication;

import static com.example.myapplication.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class RegisterMainActivity extends AppCompatActivity {

    private Button Createaccount;
    private EditText inputname,inputemail,inputpassword,inputaddress,inputnumber;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_register_main);

        Createaccount=(Button) findViewById(R.id.btnregister);
        inputname=(EditText) findViewById(R.id.editname);
        inputemail=(EditText) findViewById(R.id.editemail);
        inputpassword=(EditText) findViewById(R.id.editPassword);
        inputaddress=(EditText) findViewById(R.id.editAddress);
        inputnumber=(EditText) findViewById(R.id.editPhone);
        loadingBar=new ProgressDialog(this);

        Createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createAccount();
            }
        });


    }

    private void createAccount() {
        String name=inputname.getText().toString();
        String email=inputemail.getText().toString();
        String password=inputpassword.getText().toString();
        String address=inputaddress.getText().toString();
        String number=inputnumber.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your name...",Toast.LENGTH_LONG);
        }
       else if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please write your Email...",Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please write your Password...",Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(address)){
            Toast.makeText(this,"Please write your Address...",Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(number)){
            Toast.makeText(this,"Please write your Number...",Toast.LENGTH_LONG);
        }
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setTitle("plaease wait ,while we are checking the crentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatenumber(name,email,password,address,number);

        }



    }

    private void validatenumber(String name, String email, String password, String address, String number)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(!(snapshot.child("users").child(number).exists()))
                {
                    HashMap<String,Object> userhashmap=new HashMap<>();
                    userhashmap.put("name",name);
                    userhashmap.put("email",email);
                    userhashmap.put("password",password);
                    userhashmap.put("address",address);
                    userhashmap.put("number",number);

                    RootRef.child("users").child(number).updateChildren(userhashmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterMainActivity.this,"congratulation,your account has been created",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent =new Intent(RegisterMainActivity.this,Lodinuser.class);
                                        startActivity(intent);


                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterMainActivity.this,"Network error please try agin",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
                else
                {
                    Toast.makeText(RegisterMainActivity.this,"This"+number+"already exsists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterMainActivity.this,"plaease try again using another number",Toast.LENGTH_SHORT).show();

                    Intent intent =new Intent(RegisterMainActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


    }
}