package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Prevaent.Prevalent;
import com.example.myapplication.model.users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Lodinuser extends AppCompatActivity
{
    private EditText phonenumber,password;
    private Button login;
    private ProgressDialog loadingBar;

    private String parentDbname="users";
    private CheckBox checkremember;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodinuser);


        login=(Button) findViewById(R.id.btnlog);
        phonenumber=(EditText) findViewById(R.id.inputnumber);
        password=(EditText) findViewById(R.id.inputpassword);
        loadingBar=new ProgressDialog(this);

        checkremember=(CheckBox)findViewById(R.id.editcheckBox);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loginuser();
            }
        });

    }

    private void Loginuser() {
        String Pass=password.getText().toString();
        String number=phonenumber.getText().toString();

        if(TextUtils.isEmpty(Pass)){
            Toast.makeText(this,"Please write your Password...",Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(number)){
            Toast.makeText(this,"Please write your Number...",Toast.LENGTH_LONG);
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setTitle("plaease wait ,while we are checking the crentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowaccessAccount(number,Pass);
        }
    }

    private void AllowaccessAccount(String number, String pass)
    {
        if(checkremember.isChecked())
        {
            Paper.book().write(Prevalent.Userphonekey,number);
            Paper.book().write(Prevalent.Userpasswordkey,pass);
        }

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbname).child(number).exists())
                {
                    users userdata=snapshot.child(parentDbname).child(number).getValue(users.class);

                    if(userdata.getNumber().equals(number))
                    {
                        if(userdata.getPassword().equals(pass))
                        {

                                    if(parentDbname.equals("users"))
                                    {
                                        Toast.makeText(Lodinuser.this,"logged in successfully",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent =new Intent(Lodinuser.this,AllProduct.class);
                                        Prevalent.currentonlineuser=userdata;
                                        startActivity(intent);



                                    }


                        }
                        else
                        {
                            Toast.makeText(Lodinuser.this,"Password is incorrect",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                        }

                    }
                }
                else
                {
                    Toast.makeText(Lodinuser.this,"Account with this"+number+"number do not exsists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Lodinuser.this,"You need to account",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}