package com.example.finalapplication.Doctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.finalapplication.Module.Topicdoc;
import com.example.finalapplication.Module.Topics;
import com.example.finalapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddTopicsScreen extends AppCompatActivity {

    ImageView imageView;
    VideoView videoView;
    Button Choosevideo;
    Uri videouri;
    MediaController mediaController;
    Button chooseimage;
    EditText address;
    EditText cotent;
    Button add_btn;
    Uri imageUri;
    StorageReference storageReference;
    StorageReference storageReference2;


    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topics_screen);

        imageView=findViewById(R.id.image_add);
        chooseimage=findViewById(R.id.choose_image);
        videoView=findViewById(R.id.videoView);
        Choosevideo=findViewById(R.id.choose_video);
        videoView.setMediaController(mediaController);
        videoView.start();
        add_btn=findViewById(R.id.add_btn);
        cotent=findViewById(R.id.topic_details);
        address=findViewById(R.id.topic_address);
        firebaseFirestore=FirebaseFirestore.getInstance();


        Choosevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVideo();
            }
        });


        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimage();
                uploadVideo();

            }
        });
    }

    public void openVideo(){

        Intent intent=new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,101);

    }
    public  void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData()!= null){
            imageUri=data.getData();
            imageView.setImageURI(imageUri);

        }else if (requestCode == 101 && data != null && data.getData()!= null){
            videouri=data.getData();
            videoView.setVideoURI(videouri);
        }
    }



    public void  uploadVideo(){
        storageReference= FirebaseStorage.getInstance().getReference("videos/");
        storageReference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String title=address.getText().toString();

                        Map<String, Object> user = new HashMap<>();
                        user.put("title", title.toString());
                        firebaseFirestore.collection("topicDOC").document().set(
                                new Topicdoc(title,uri.toString())
                        );
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
    public void uploadimage() {
        storageReference = FirebaseStorage.getInstance().getReference("images/");
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageView.setImageURI(null);

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String title=address.getText().toString();
                        String content=cotent.getText().toString();

                        Map<String, Object> user = new HashMap<>();
                        user.put("title", title.toString());
                        user.put("content", content.toString());
                        firebaseFirestore.collection("Topics").document().set(
                                new Topics(title,content,uri.toString())
                        );
                    }
                });

                Toast.makeText(AddTopicsScreen.this, "uploaded", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddTopicsScreen.this, DoctorHome.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTopicsScreen.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
