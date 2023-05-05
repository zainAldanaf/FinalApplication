package com.example.finalapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalapplication.Patient.Client_registration;

public class MainActivity extends AppCompatActivity {
CardView patientCard, doctorCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patientCard = findViewById(R.id.patientCard);
        doctorCard = findViewById(R.id.doctorCard);


        patientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Client_registration.class));

            }
        });

        doctorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DoctorSign.class));

            }
        });
    }
}