package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Prevaent.Prevalent;
import com.example.myapplication.model.users;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {


    private EditText nameedittxt,pohineedittxt,addressadittxt,emailedittxt,passwordedittxt;
    Button btnsh;
    TextView btnup;
    private TextView btndele;
    DatabaseReference dbref;
    users prof;


    public void clearcontrol(){
        nameedittxt.setText("");
        pohineedittxt.setText("");
        addressadittxt.setText("");
        emailedittxt.setText("");
        passwordedittxt.setText("");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        nameedittxt=findViewById(R.id.setting_name);
        pohineedittxt=findViewById(R.id.setting_phone);
        addressadittxt= findViewById(R.id.setting_address);
        emailedittxt=findViewById(R.id.email_settig);
        passwordedittxt= findViewById(R.id.setting_password);

        btnsh=findViewById(R.id.showbtn);
        btndele=findViewById(R.id.btndelete);
        btnup=findViewById(R.id.btnupdate);

        prof=new users();

        btnsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("users").child("0787969803");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            nameedittxt.setText(dataSnapshot.child("name").getValue().toString());
                            pohineedittxt.setText(dataSnapshot.child("number").getValue().toString());
                            addressadittxt.setText(dataSnapshot.child("address").getValue().toString());
                            emailedittxt.setText(dataSnapshot.child("email").getValue().toString());
                            passwordedittxt.setText(dataSnapshot.child("password").getValue().toString());

                        }



                        else
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {



                    }
                });
            }
        });

        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("users").child(Prevalent.currentonlineuser.getNumber());
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(Prevalent.currentonlineuser.getNumber())){
                            try{
                                prof.setName(nameedittxt.getText().toString().trim());
                                prof.setEmail(emailedittxt.getText().toString().trim());
                                prof.setPassword(passwordedittxt.getText().toString().trim());
                                prof.setAddress(addressadittxt.getText().toString().trim());
                                prof.setNumber(pohineedittxt.getText().toString().trim());




                                dbref= FirebaseDatabase.getInstance().getReference().child("users").child(Prevalent.currentonlineuser.getNumber());
                                dbref.setValue(prof);
                                clearcontrol();



                                Toast.makeText(getApplicationContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                            }



                            catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Source to Update", Toast.LENGTH_SHORT).show();
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {



                    }
                });
            }
        });

        btndele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("users");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(Prevalent.currentonlineuser.getNumber())) {
                            dbref = FirebaseDatabase.getInstance().getReference().child().child(Prevalent.currentonlineuser.getNumber());
                            dbref.removeValue();
                            clearcontrol();
                            Toast.makeText(getApplicationContext(), "Deleted user", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                            startActivity(intent);
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
