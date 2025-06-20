package com.example.indie_sell_buy;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    EditText editTextusername,editTextpassword;
    Button buttonLogin;
    TextView btnSignup;
    ProgressBar progress;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextusername = findViewById(R.id.Username);
        editTextpassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.Login);
        btnSignup = findViewById(R.id.btnSignup);
        progressBar = findViewById(R.id.progress);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Username,password;
                Username = valueOf(editTextusername.getText());
                password = valueOf(editTextpassword.getText());

                showProgressBar();
                if(!Username.isEmpty()){
                    editTextusername.setError(null);
                    if(!password.isEmpty()){
                        editTextpassword.setError(null);

                        final String Username_data = valueOf(editTextusername.getText());
                        final String password_data = valueOf(editTextpassword.getText());
                        //firebase auth..
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference("users");
                        //checking if users is exits..
                        Query check_username = databaseReference.orderByChild("username").equalTo(Username_data);
                        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //checking condition of users and password in firebase..
                                if(snapshot.exists()){
                                    editTextusername.setError(null);
                                    String password_check = snapshot.child(Username_data).child("password").getValue(String.class);
                                    if(password_check.equals(password_data)){
                                        editTextpassword.setError(null);
                                        Toast.makeText(getApplicationContext(), "loging succesfully", Toast.LENGTH_SHORT).show();
                                        hideProgressBar();
                                        Intent intent = new Intent(getApplicationContext(),SellerAddProduct.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        editTextpassword.setError("password is incorrect!");
                                    }
                                }
                                else{
                                    editTextusername.setError("user does not exists!");
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                        editTextpassword.setError("please enter the password!");
                    }
                }
                else{
                    editTextusername.setError("please enter the username!");
                }
            }

            //giving the progress bar
            private void showProgressBar() {
                progressBar.setVisibility(View.VISIBLE);
            }
            private void hideProgressBar() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}