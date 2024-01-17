package com.example.ados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ados.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sprofile extends AppCompatActivity {

    TextView SName, SPhone, SEmail, SPassword;
    Button Sshowbtn;

    DatabaseReference sdbref;
    Users suser;

    private void sclear(){
        SName.setText("");
        SPhone.setText("");
        SEmail.setText("");
        SPassword.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprofile);

        SName = findViewById(R.id.Sprofname);
        SPhone = findViewById(R.id.Sprofphone);
        SEmail = findViewById(R.id.Sprofemail);
        SPassword = findViewById(R.id.Sprofpw);


        suser = new Users();

        Sshowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference selRef = FirebaseDatabase.getInstance().getReference().child("Users").child("ee");
                selRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChildren()) {
                            SEmail.setText(dataSnapshot.child("email").getValue().toString());
                            SName.setText(dataSnapshot.child("name").getValue().toString());
                            SPassword.setText(dataSnapshot.child("password").getValue().toString());
                            SPhone.setText(dataSnapshot.child("phone").getValue().toString());

                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}

