package com.example.finalapplication.Fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalapplication.R;
import com.example.finalapplication.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ProfileFragment extends Fragment {
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
     private FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        binding = FragmentProfileBinding.inflate(getLayoutInflater());

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        uid = firebaseAuth.getUid().toString();
        Log.e(TAG, uid);

        fullname = binding.profilename;
        birthdate = binding.birthDateprofile;
        address1 = binding.addressprofile;
        emailaddress = binding.emailprofile;
        phone_number = binding.phoneprofile;
        password = binding.passwordProfile;

         database = FirebaseDatabase.getInstance();
         ref = database.getReference("Patients");

        getProfile();

        return binding.getRoot();
    }

    public  void getProfile(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Iterate through the data and retrieve the values
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
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

}



/*  db.collection("Patients").document(uid.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               if (documentSnapshot.exists()){
                  String name=documentSnapshot.getString("fullname");
                   String email=documentSnapshot.getString("email");
                   String address=documentSnapshot.getString("address");
                   String birth_date=documentSnapshot.getString("birthdate");
                   String password=documentSnapshot.getString("password");
                   String phonenumber=documentSnapshot.getString("phone");
//
                   fullname.setText(name);
                   address1.setText(address);
                   emailaddress.setText(email);
                   birthdate.setText(birth_date);
                   pass.setText(password);
                   phone_number.setText(phonenumber);

               }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "failed!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });
*/