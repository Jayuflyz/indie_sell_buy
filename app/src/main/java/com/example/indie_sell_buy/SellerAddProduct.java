package com.example.indie_sell_buy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.File;
import java.text.BreakIterator;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SellerAddProduct extends AppCompatActivity {

    ImageView ProductUpload;
    String ImageUrl;
    Uri uri;
    EditText Productname, Productdescription, Productprice, ProductDiscountedprice;
    Button ProductSubmitbtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_product);

        ProductUpload = findViewById(R.id.ProductUpload);
        Productname = findViewById(R.id.ProductName);
        Productdescription = findViewById(R.id.ProductDescription);
        Productprice = findViewById(R.id.ProductPrice);
        ProductDiscountedprice = findViewById(R.id.ProductDiscountedPrice);
        ProductSubmitbtn = findViewById(R.id.ProductSubmit);

        Spinner carcompanySpinner = (Spinner) findViewById(R.id.CarCompanySpinner);
        ArrayAdapter<CharSequence> carcompanyAdapter = ArrayAdapter.createFromResource(this, R.array.CarCompanies, android.R.layout.simple_spinner_item);
        carcompanyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carcompanySpinner.setAdapter(carcompanyAdapter);

        Spinner carTypeSpinner = (Spinner) findViewById(R.id.CarTypeSpinner);
        ArrayAdapter<CharSequence> carTypeAdapter = ArrayAdapter.createFromResource(this, R.array.CarType, android.R.layout.simple_spinner_item);
        carTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carTypeSpinner.setAdapter(carTypeAdapter);

        Spinner CarCategorySpinner = (Spinner) findViewById(R.id.CarCategorySpinner);
        ArrayAdapter<CharSequence> CarCategoryAdapter = ArrayAdapter.createFromResource(this, R.array.CarCategory, android.R.layout.simple_spinner_item);
        CarCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CarCategorySpinner.setAdapter(CarCategoryAdapter);

        Spinner CarFueltypeSpinner = (Spinner) findViewById(R.id.CarFueltypeSpinner);
        ArrayAdapter<CharSequence> CarFueltypeAdapter = ArrayAdapter.createFromResource(this, R.array.CarFueltype, android.R.layout.simple_spinner_item);
        CarFueltypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CarFueltypeSpinner.setAdapter(CarFueltypeAdapter);

        // image upload.....
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if(result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            uri =  data.getData();
                            ProductUpload.setImageURI(uri);
                        }
                        else {
                            Toast.makeText(SellerAddProduct.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // setting up image upload button with view of respective view of image..
        ProductUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        // setting up for the full product submit with fields and all images...
        ProductSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();
                saveData();
            }
        });
    }

    private void saveData() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Product Image")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(SellerAddProduct.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                ImageUrl = urlImage.toString();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData() {

        String Pname = Productname.getText().toString();
        String Pdescription = Productdescription.getText().toString();
        String Price = Productprice.getText().toString();
        String Pdiscountedprice = ProductDiscountedprice.getText().toString();

        if (!Pname.isEmpty()) {
            Productname.setError(null);
            if (!Pdescription.isEmpty()) {
                Productdescription.setError(null);
                if (!Price.isEmpty()) {
                    Productprice.setError(null);
                    if (!Pdiscountedprice.isEmpty()) {
                        ProductDiscountedprice.setError(null);
                        //firebase inserting fields dataa.......
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        reference = firebaseDatabase.getReference("product");

                        String Pname_s = String.valueOf(Productname.getText());
                        String Pdesc_s = String.valueOf(Productdescription.getText());
                        String price_s = String.valueOf(Productprice.getText());
                        String Pdiscountedprice_s = String.valueOf(ProductDiscountedprice.getText());
                        //making the object of the storinguserdataclass..
                        StoringProductdataClass storingProductdataClass = new StoringProductdataClass(Pname_s, Pdesc_s, price_s, Pdiscountedprice_s);
                        //making the node type reffrence using the userfield...
                        reference.child(Pname_s).setValue(StoringProductdataClass.class);
                        //navigation to the login screen after signup
                        Toast.makeText(getApplicationContext(), "Product added", Toast.LENGTH_SHORT).show();

                    } else {
                        ProductDiscountedprice.setError("Please Enter the Product Discounted Price!");
                    }
                } else {
                    Productprice.setError("Please Enter the Product Price!");
                }
            } else {
                ProductDiscountedprice.setError("Please Enter the Product Description!");
            }
        } else {
            Productname.setError("Please Enter the Product Name!");
        }
    }
    }

