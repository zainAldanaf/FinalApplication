package com.example.finalapplication.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalapplication.PationtsModule.PationtsModule;
import com.example.finalapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Client_registration extends AppCompatActivity {
    Button Sign_btn;
    TextView haveAccount;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    EditText fullname;
    EditText birth_date;
    EditText address1;
    EditText emailaddress;
    EditText phone_number;
    EditText pass;
    EditText confirmpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_registration);

        Sign_btn = findViewById(R.id.register);
        fullname = findViewById(R.id.nameTxt);
        birth_date = findViewById(R.id.birthTxt);
        address1 = findViewById(R.id.addressTxt);
        emailaddress = findViewById(R.id.emailTxt);
        phone_number = findViewById(R.id.poneTxt);
        pass = findViewById(R.id.passTxt);
        confirmpass = findViewById(R.id.confirmPass);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        Sign_btn = findViewById(R.id.register);
        haveAccount = findViewById(R.id.dont_have_account);

        haveAccount.setOnClickListener(view ->{
            startActivity(new Intent(Client_registration.this,Client_login.class));

        });


        Sign_btn.setOnClickListener(view ->{
            createUser();
        });
    }
    private void createUser(){
        String email = emailaddress.getText().toString();
        String password = pass.getText().toString();
        String address = address1.getText().toString();
        String birthdate= birth_date.getText().toString();
        String name = fullname.getText().toString();
        String confrimpassword = confirmpass.getText().toString();
        String phone = phone_number.getText().toString();

        if (TextUtils.isEmpty(email)){
            emailaddress.setError("Email cannot be empty");
            emailaddress.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            pass.setError("Password cannot be empty");
            pass.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            fullname.setError("Password cannot be empty");
            fullname.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            address1.setError("Password cannot be empty");
            address1.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            phone_number.setError("Password cannot be empty");
            phone_number.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            confirmpass.setError("Password cannot be empty");
            confirmpass.requestFocus();
        }else{
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Client_registration.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Client_registration.this, Client_login.class));
                        firebaseFirestore.collection("Patients").document(FirebaseAuth.getInstance().getUid()).set(
                                new PationtsModule(name,address,birthdate,email,phone,password,confrimpassword)
                        );
                    }else{
                        Toast.makeText(Client_registration.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}