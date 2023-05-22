package com.example.finalapplication.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.finalapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorProfile extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    DatabaseReference ref;
    FirebaseDatabase database;
    FirebaseUser firebaseAuth;

    TextView fullname;
    TextView birthdate;
    TextView address1;
    TextView emailaddress;
    TextView phone_number;
    String uid;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        fullname= findViewById(R.id.profilename);
        birthdate= findViewById(R.id.birth_dateprofile);
        address1= findViewById(R.id.addressprofile);
        emailaddress= findViewById(R.id.emailprofile);
        phone_number= findViewById(R.id.phoneprofile);
        password= findViewById(R.id.password_profile);

        uid = firebaseAuth.getUid().toString();
        Log.e("TAG", uid);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Doctors");

        getProfile();



    }
    public void getProfile(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("fullname").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    String birthdate2 = snapshot.child("birthdate").getValue(String.class);
                    String pass = snapshot.child("password").getValue(String.class);

                    fullname.setText(name);
                    emailaddress.setText(email);
                    address1.setText(address);
                    phone_number.setText(phone);
                    birthdate.setText(birthdate2);
                    password.setText(pass);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }

        });

    }
}