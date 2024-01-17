package com.example.ados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ados.Model.Users;
import com.example.ados.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeliveryProfile extends AppCompatActivity {

    EditText txtName,txtType,txtEmail,txtConNum,textpw;
    Button btnshow,btnupdate;
    DatabaseReference dbRef;
    Users profd;

    private void clearControl(){
        txtName.setText("");
        txtType.setText("");
        txtEmail.setText("");
        txtConNum.setText("");
        textpw.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_profile);


        txtName = findViewById(R.id.proname);
        txtType = findViewById(R.id.protype);
        txtEmail = findViewById(R.id.proemail);
        txtConNum= findViewById(R.id.protel);
        textpw = findViewById(R.id.propw);

        btnshow = findViewById(R.id.BtnproShow);
        btnupdate = findViewById(R.id.BtnproUpdate);

        profd = new Users();



        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Users").child("0717701206");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            txtName.setText(dataSnapshot.child("name").getValue().toString());
                            txtType.setText(dataSnapshot.child("type").getValue().toString());
                            txtEmail.setText(dataSnapshot.child("email").getValue().toString());
                            txtConNum.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                            textpw.setText(dataSnapshot.child("password").getValue().toString());
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



        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Users");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("0717701206")){
                            try{
                                profd.setName(txtName.getText().toString().trim());
                                profd.setType(txtType.getText().toString().trim());
                                profd.setEmail(txtEmail.getText().toString().trim());
                                profd.setPhoneNumber(txtConNum.getText().toString().trim());
                                profd.setPassword(textpw.getText().toString().trim());


                                dbRef= FirebaseDatabase.getInstance().getReference().child("Users").child("0717701206");
                                dbRef.setValue(profd);
                                clearControl();



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
   }
}