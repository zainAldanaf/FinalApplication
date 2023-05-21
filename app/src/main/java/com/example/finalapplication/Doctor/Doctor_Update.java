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
    private String currentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update);

        firebaseAuth=FirebaseAuth.getInstance();
        namee=findViewById(R.id.edittopic_address);
        detailss=findViewById(R.id.edittopic_details);
        edit_edit=findViewById(R.id.editadd_btn);
        edit_img=findViewById(R.id.editchoose_image);
        edit_video=findViewById(R.id.editchoose_video);



        String newName = namee.getText().toString();
        String newDetails = detailss.getText().toString();
        String newImg = edit_img.getText().toString();
        String newVideo = edit_video.getText().toString();

        edit_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_topic_doc(newName,newDetails,newImg);
            }
        });






    }

    public void update_topic_doc(final String name,String details, String img){
        currentid=firebaseAuth.getCurrentUser().getUid();

        db.collection("Topics").document(currentid)
                .update("topic_name",namee.getText().toString(),
                        "topic_content",detailss.getText().toString(),
                        "topic_image",edit_img.getText().toString())

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Doctor_Update.this,"update successfully", Toast.LENGTH_LONG).show();

//                         db.collection("profile").document(firebaseAuth.getUid())
//                                 .set(new profile_data(name,email,address,phone));

                        Intent intent=new Intent(Doctor_Update.this,DoctorHome.class);
                        startActivity(intent);


                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("edit", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("edit", "Error updating document", e);
                    }
                });

    }
}