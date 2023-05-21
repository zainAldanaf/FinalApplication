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

import com.example.finalapplication.Module.doctorModule;
import com.example.finalapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorSign extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;
    Button Sign_btn;
    EditText fullname;
    EditText birth_date;
    EditText address1;
    EditText emailaddress;
    TextView haveAccount;
    EditText phone_number;
    EditText pass;
    EditText confirmpass;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign);
        Sign_btn = findViewById(R.id.register2);
        fullname = findViewById(R.id.nameTxt2);
        birth_date = findViewById(R.id.birthTxt2);
        address1 = findViewById(R.id.addressTxt2);
        emailaddress = findViewById(R.id.emailTxt2);
        phone_number = findViewById(R.id.ponetxt2);
        pass = findViewById(R.id.password2);
        confirmpass = findViewById(R.id.confirmPass2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Doctors");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        progressDialog = new ProgressDialog(this);
        haveAccount = findViewById(R.id.haveAccount2);
        Sign_btn.setOnClickListener(view -> {
            createDoctor();
        });

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorSign.this, DoctorLogin.class));

            }
        });
    }

    private void createDoctor() {
        String name = fullname.getText().toString();
        String email = emailaddress.getText().toString();
        String password = pass.getText().toString();
        String address = address1.getText().toString();
        String birthdate = birth_date.getText().toString();
        String confrimpassword = confirmpass.getText().toString();
        String phone = phone_number.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailaddress.setError("Email cannot be empty");
            emailaddress.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            pass.setError("name cannot be empty");
            pass.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            fullname.setError("Password cannot be empty");
            fullname.requestFocus();
        } else if (TextUtils.isEmpty(birthdate)) {
            address1.setError("birthdate cannot be empty");
            address1.requestFocus();
        } else {

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        doctorModule doctorModule = new doctorModule(name, address, birthdate, email, phone, password, confrimpassword);
                        databaseReference.child(name).setValue(doctorModule);
                        Intent intent = new Intent(DoctorSign.this, DoctorLogin.class);
                        startActivity(intent);
                        progressDialog.cancel();
                    } else {
                        Toast.makeText(DoctorSign.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                }
            });
        }
    }
}

