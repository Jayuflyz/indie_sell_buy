package com.example.indie_sell_buy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText editText_email, editText_username, editText_password, editText_mobileno;
    Button buttonSignup;
    TextView textViewbtnLogin;
    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editText_email = findViewById(R.id.email);
        editText_username = findViewById(R.id.username);
        editText_password = findViewById(R.id.password);
        editText_mobileno = findViewById(R.id.mobileno);
        buttonSignup = findViewById(R.id.Signup);
        textViewbtnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressbar);

        textViewbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email,username,password,mobileno;
                email = String.valueOf(editText_email.getText());
                username = String.valueOf(editText_username.getText());
                password = String.valueOf(editText_password.getText());
                mobileno = String.valueOf(editText_mobileno.getText());

                showProgressBar();
                if (!email.isEmpty()){
                    editText_email.setError(null);
                    if(!username.isEmpty()){
                        editText_username.setError(null);
                        if(!password.isEmpty()){
                            editText_password.setError(null);
                            if(!mobileno.isEmpty()){
                                editText_mobileno.setError(null);
                                //firebase auth
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                reference = firebaseDatabase.getReference("users");

                                String email_s = String.valueOf(editText_email.getText());
                                String username_s = String.valueOf(editText_username.getText());
                                String password_s = String.valueOf(editText_password.getText());
                                String mobileno_s = String.valueOf(editText_mobileno.getText());
                                //making the object of the storinguserdataclass..
                                storinguserdataClass storeuserdata = new storinguserdataClass(email_s,username_s,password_s,mobileno_s);
                                //making the node type reffrence using the userfield...
                                reference.child(username_s).setValue(storeuserdata);
                                //navigation to the login screen after signup
                                Toast.makeText(getApplicationContext(), "Welcome to indieparts", Toast.LENGTH_SHORT).show();
                                hideProgressBar();
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                                finish();
                                progressBar.setVisibility(View.GONE);
                            }
                            else{
                                editText_mobileno.setError("please enter the mobile no!");
                            }
                        }
                        else{
                            editText_password.setError("please enter the password!");
                        }
                    }
                    else{
                        editText_username.setError("please enter the username!");
                    }
                }
                else{
                    editText_email.setError("please enter the email!");
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