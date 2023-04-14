package com.example.finalapplication.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DoctorHome extends AppCompatActivity {
FloatingActionButton fba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        fba=findViewById(R.id.fab);
        fba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorHome.this, AddTopicsScreen.class));

            }
        });
    }
}