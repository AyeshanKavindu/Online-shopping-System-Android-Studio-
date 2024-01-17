package com.example.myapplication;

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

public class creditcard extends AppCompatActivity {

    private Button creatbtn;
    private EditText cnumber,enumber,cvcnumber;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditcard);

        creatbtn=(Button) findViewById(R.id.createpay);
        cnumber=(EditText)findViewById(R.id.cardnum);
        enumber=(EditText)findViewById(R.id.enumber);
        cvcnumber=(EditText)findViewById(R.id.cvcnum);
        loadingBar=new ProgressDialog(this);

        creatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createpayment();
            }
        });


    }

    private void createpayment() {
        String cardnum=cnumber.getText().toString();
        String expiren=enumber.getText().toString();
        String cvc=cvcnumber.getText().toString();



        if(TextUtils.isEmpty(cardnum)){
            Toast.makeText(this,"Please write your card number...",Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(expiren)){
            Toast.makeText(this,"Please write your Expire date...",Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(cvc)){
            Toast.makeText(this,"Please write your CVC number...",Toast.LENGTH_LONG);
        }

        else {
            loadingBar.setTitle(" cretae payment");
            loadingBar.setTitle("plaease wait ,while we are checking the crentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatenumber(cardnum,expiren,cvc);

        }


    }

    private void validatenumber(String cardnum, String expiren, String cvc) {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    HashMap<String,Object> userhashmap=new HashMap<>();
                    userhashmap.put("card number",cardnum);
                    userhashmap.put("expire date",expiren);
                    userhashmap.put("CVC",cvc);



                    RootRef.child("payment").child(cardnum).updateChildren(userhashmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(creditcard.this,"congratulation,your payment success.",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent =new Intent(creditcard.this,phones.class);
                                        startActivity(intent);


                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(creditcard.this,"Network error please try agin",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}