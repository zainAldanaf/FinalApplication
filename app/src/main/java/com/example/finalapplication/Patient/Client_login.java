package com.example.finalapplication.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Client_login extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    EditText email1;
    EditText pass;
    Button btn_login;
    TextView dont_have_account;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        email1 = findViewById(R.id.emailLogin);
        pass = findViewById(R.id.passLogin);
        btn_login = findViewById(R.id.login);
        dont_have_account = findViewById(R.id.dont_have_account);

        dont_have_account.setOnClickListener(view ->{
            startActivity(new Intent(Client_login.this,Client_registration.class));

        });

        btn_login.setOnClickListener(view ->{
            loginUser();

        });
    }

    private void loginUser(){
        String email = email1.getText().toString();
        String password = pass.getText().toString();

        if (TextUtils.isEmpty(email)){
            email1.setError("Email cannot be empty");
            email1.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            pass.setError("Password cannot be empty");
            pass.requestFocus();
        }else{
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Client_login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Client_login.this, HomeScreen.class));
                    }else{
                        Toast.makeText(Client_login.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}