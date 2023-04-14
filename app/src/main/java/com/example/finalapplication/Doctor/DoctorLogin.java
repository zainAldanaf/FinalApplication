package com.example.finalapplication.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalapplication.Patient.Client_registration;
import com.example.finalapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorLogin extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    EditText email1;
    EditText pass;
    Button btn_login1;
    TextView dont_have_account;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        email1 = findViewById(R.id.emailLogin2);
        pass = findViewById(R.id.passLogin2);
        btn_login1 = findViewById(R.id.logindoctor);
        dont_have_account = findViewById(R.id.donthaveAccount2);

        progressDialog=new ProgressDialog(this);
        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            loginUser();

            }
        });
        dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorLogin.this, Client_registration.class));


            }
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
                        Toast.makeText(DoctorLogin.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(DoctorLogin.this, DoctorHome.class));
                        progressDialog.cancel();
                    }else{
                        Toast.makeText(DoctorLogin.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                }
            });
        }
    }


}