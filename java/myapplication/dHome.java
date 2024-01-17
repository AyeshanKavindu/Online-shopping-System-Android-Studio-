package com.example.lfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Users;
import Privalent.Privalent;
import io.paperdb.Paper;

public class dHome extends AppCompatActivity {

    private EditText InputNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhome);

        InputNumber = (EditText) findViewById(R.id.logPhone);
        InputPassword = (EditText) findViewById(R.id.LogPassword);
        LoginButton = (Button) findViewById(R.id.LogButton);
        loadingBar = new ProgressDialog(this);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.check_Box);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginUser();
            }
        });

        textView = (TextView) findViewById(R.id.txt_view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dHome.this, register.class);
                startActivity(intent);
                Toast.makeText(dHome.this, "Create New Account Here", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void LoginUser()
    {
        String telephone = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(telephone))
        {
            Toast.makeText(this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, While we are checking the credential");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount (telephone,password);
        }
    }

    private void AllowAccessToAccount(String telephone, String password)
    {
        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Privalent.UserPhoneKey,telephone);
            Paper.book().write(Privalent.UserPassword,password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(telephone).exists())
                {
                    Users userData = snapshot.child(parentDbName).child(telephone).getValue(Users.class);

                    if(userData.getTelephone().equals(telephone))
                    {
                        if(userData.getPassword().equals(password))
                        {

                            Toast.makeText(dHome.this, "Logged in successfuly...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(dHome.this, Uprofile.class);
                            startActivity(intent);



                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(dHome.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(dHome.this, "Account with this"+telephone+"number do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}