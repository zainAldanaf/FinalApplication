package com.example.finalapplication.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalapplication.Module.showDoctor;
import com.example.finalapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Doctor_Update extends AppCompatActivity {

    EditText namee;
    EditText detailss;
    Button edit_img;
    Button edit_video;
    Button edit_edit;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth firebaseAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update);

        namee = findViewById(R.id.edittopic_address);
        detailss = findViewById(R.id.edittopic_details);
        edit_edit = findViewById(R.id.editadd_btn);

        String title = namee.getText().toString();
        String details = detailss.getText().toString();


        edit_edit.setOnClickListener(view ->{
           // updateUser();
        });

    }
        public void updateUser(final showDoctor showdoctor) {

            db.collection("DoctorTopics").document(showdoctor.getId()).update(
                            "topic_address", namee.getText().toString(),
                            "topic_details",detailss.getText().toString()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("zzz", "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("zzz", "Error updating document", e);
                        }
                    });
        }

    }
