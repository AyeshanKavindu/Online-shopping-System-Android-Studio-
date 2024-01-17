package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Prevaent.Prevalent;
import com.example.myapplication.model.users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button btnlogin,btnreg;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin=(Button) findViewById(R.id.btnlog);
        btnreg=(Button) findViewById(R.id.btnjoin);
        loadingBar=new ProgressDialog(this);

        Paper.init(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,AllusersLogin.class);
                startActivity(intent);
            }
        });
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this,AlluserReg.class);
                startActivity(i);
            }
        });

        String userphonekey=Paper.book().read(Prevalent.Userphonekey);
        String userpasswordkey=Paper.book().read(Prevalent.Userpasswordkey);
        if(userphonekey !="" && userpasswordkey !="")
        {
            if(!TextUtils.isEmpty(userphonekey) && !TextUtils.isEmpty(userpasswordkey))
            {
                Allowaccess(userphonekey,userpasswordkey);

                loadingBar.setTitle("Already login");
                loadingBar.setTitle("plaease wait....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }



    }

    private void Allowaccess(String number, String pass)
    {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("users").child(number).exists())
                {
                    users userdata=snapshot.child("users").child(number).getValue(users.class);

                    if(userdata.getNumber().equals(number))
                    {
                        if(userdata.getPassword().equals(pass))
                        {
                            Toast.makeText(MainActivity.this,"please wait,you are already logged",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent =new Intent(MainActivity.this,AllProduct.class);
                            Prevalent.currentonlineuser=userdata;
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Password is incorrect",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                        }

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Account with this"+number+"number do not exsists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this,"You need to account",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}