package com.example.ados;

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

public class DeliveryRegister extends AppCompatActivity {

    private Button DregButton;
    private EditText DRname,DRemail,DRPhoneNumber,DRpw,DRtype;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_register);

        DregButton = (Button) findViewById(R.id.Dregbutton);
        DRname = (EditText) findViewById(R.id.proname);
        DRtype = (EditText) findViewById(R.id.protype);
        DRemail = (EditText) findViewById(R.id.proemail);
        DRPhoneNumber = (EditText) findViewById(R.id.protel);
        DRpw = (EditText) findViewById(R.id.DRpw);
        loadingbar = new ProgressDialog(this);


        DregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateDeliveryAcc();
            }
        });
    }

    private void CreateDeliveryAcc() {
        String name = DRname.getText().toString();
        String emil = DRemail.getText().toString();
        String type = DRtype.getText().toString();
        String Password = DRpw.getText().toString();
        String PhoneNumber = DRPhoneNumber.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"please write your  name..",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(emil)){
            Toast.makeText(this,"please write your email..",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Password)){
            Toast.makeText(this,"please write your password..",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(type)){
            Toast.makeText(this,"please write your type..",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(PhoneNumber)){
            Toast.makeText(this,"please write your Phone Number..",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar.setTitle("Create new Delivery Account");
            loadingbar.setMessage("Please wait,while we are checking the credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            ValidatePhoneNumber(name,emil,Password,PhoneNumber,type);
        }

    }

    private void ValidatePhoneNumber(String name, String email, String password, String PhoneNumber,String type) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(PhoneNumber).exists()))
                {
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("name",name);
                    userdataMap.put("email",email);
                    userdataMap.put("password",password);
                    userdataMap.put("phoneNumber",PhoneNumber);
                    userdataMap.put("type",type);

                    RootRef.child("Users").child(PhoneNumber).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(DeliveryRegister.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();

                                        Intent intent = new Intent(DeliveryRegister.this,DeliveryLogin.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        loadingbar.dismiss();
                                        Toast.makeText(DeliveryRegister.this, "Error..", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
                else{
                    Toast.makeText(DeliveryRegister.this, "This" + PhoneNumber +  "already exists", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(DeliveryRegister.this, "Please Enter another phone Number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}