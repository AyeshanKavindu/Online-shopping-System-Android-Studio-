package com.example.ados;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SelAddProduct extends AppCompatActivity {

    private String CategoryName, Description, Price, Prodname, saveCurrentDate, saveCurrentTime;
    private ImageView Prodimage;
    private Button Addprodbutton;
    private EditText InputProdName, InputProdDesc, InputProdPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_add_product);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        Prodimage = (ImageView) findViewById(R.id.Productimg);
        Addprodbutton = (Button) findViewById(R.id.Addprodbtn);
        InputProdName = (EditText) findViewById(R.id.Prodname);
        InputProdDesc = (EditText) findViewById(R.id.Proddesc);
        InputProdPrice = (EditText) findViewById(R.id.Prodprice);
        loadingBar = new ProgressDialog(this);


        Prodimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenGallery();
            }
        });

        Addprodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();

            }
        });

    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            Prodimage.setImageURI(ImageUri);

        }
    }

    private void ValidateProductData()
    {
        Description = InputProdDesc.getText().toString();
        Price = InputProdPrice.getText().toString();
        Prodname = InputProdName.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Product Image is Mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please Write Product Description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please Write Product Prices...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Prodname))
        {
            Toast.makeText(this, "Please Write Product Product Name...", Toast.LENGTH_SHORT).show();
        }
        else{
            StoreProductInformation();
        }


    }

    private void StoreProductInformation() {

        loadingBar.setTitle("Add New Product...");
        loadingBar.setMessage("Please wait, while we are adding the new product...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate = saveCurrentTime;

        StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(SelAddProduct.this, "Error: " +message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(SelAddProduct.this, "Product Image Uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();



                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(SelAddProduct.this, "Got the Product Image URL Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();

                        }
                    }
                });

            }
        });

    }

    private void SaveProductInfoToDatabase(){
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", CategoryName);
        productMap.put("price", Price);
        productMap.put("pname", Prodname);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(SelAddProduct.this,SellerProdCatg.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(SelAddProduct.this, "Product is add Sucesfully...", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(SelAddProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

}