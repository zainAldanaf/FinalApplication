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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser firebaseAuth;
    TextView fullname;
    TextView birthdate;
    TextView address1;
    TextView emailaddress;
    TextView phone_number;
    String uid;
    TextView pass;
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
        pass = binding.passwordProfile;

        getProfile();

        return binding.getRoot();
    }

    public  void getProfile(){

        db.collection("Patients").document(uid.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

    }

}