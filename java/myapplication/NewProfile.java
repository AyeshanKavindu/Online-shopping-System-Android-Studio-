package com.example.lfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Users;

public class NewProfile extends AppCompatActivity {

    EditText uName,uEmail,uAddress,uTelephone,uPassword;
    Button showDetails;
    TextView backNav,upNav,uDel;
    DatabaseReference db;
    Users users;

    private void clear(){
        uName.setText("");
        uEmail.setText("");
        uAddress.setText("");
        uTelephone.setText("");
        uPassword.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        uName = findViewById(R.id.u_name);
        uEmail = findViewById(R.id.u_email);
        uAddress = findViewById(R.id.u_address);
        uTelephone = findViewById(R.id.u_telephone);
        uPassword = findViewById(R.id.u_password);

        showDetails = findViewById(R.id.show_details);
        upNav = findViewById(R.id.upDate);
        uDel = findViewById(R.id.u_delete);


        users = new Users();

        showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Users").child("0786679323");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            uName.setText(dataSnapshot.child("name").getValue().toString());
                            uEmail.setText(dataSnapshot.child("email").getValue().toString());
                            uAddress.setText(dataSnapshot.child("address").getValue().toString());
                            uTelephone.setText(dataSnapshot.child("telephone").getValue().toString());
                            uPassword.setText(dataSnapshot.child("password").getValue().toString());
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
      upNav.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Users");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      if (dataSnapshot.hasChild("0786679323")){
                            try{
                                users.setName(uName.getText().toString().trim());
                                users.setEmail(uEmail.getText().toString().trim());
                                users.setAddress(uAddress.getText().toString().trim());
                                users.setTelephone(uTelephone.getText().toString().trim());
                                users.setPassword(uPassword.getText().toString().trim());

                                db= FirebaseDatabase.getInstance().getReference().child("Users").child("0786679323");
                                db.setValue(users);
                                clear();

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

        uDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Users");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("0786679323")) {
                            db = FirebaseDatabase.getInstance().getReference().child("Users").child("0786679323");
                            db.removeValue();
                            clear();
                            Intent intent = new Intent(NewProfile.this, dHome.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "User Delete Succussfully..", Toast.LENGTH_SHORT).show();
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

        backNav = (TextView) findViewById(R.id.back);
        backNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewProfile.this, Uprofile.class);
                startActivity(intent);
            }
        });

    }
}