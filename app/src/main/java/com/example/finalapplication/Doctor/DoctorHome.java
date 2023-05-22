package com.example.finalapplication.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.finalapplication.Adapter.Doctor_Adapter;
import com.example.finalapplication.Module.Topics;
import com.example.finalapplication.Module.showDoctor;
import com.example.finalapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class DoctorHome extends AppCompatActivity implements Doctor_Adapter.ItemClickListener  {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerDoc;
    Doctor_Adapter adapterDoc;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    ArrayList<showDoctor> topic_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);


        recyclerDoc = findViewById(R.id.recycler_doc);
        topic_items = new ArrayList<showDoctor>();
        adapterDoc = new Doctor_Adapter(this, topic_items, this,this);
        recyclerDoc.setAdapter(adapterDoc);

        // String nn = getIntent().getStringExtra("name");
        GetTopics();
    }

    private void GetTopics() {

        db.collection("DoctorTopic").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("get", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String name = documentSnapshot.getString("topic_address");
                                    String content = documentSnapshot.getString("topic_details");
                                    String image = documentSnapshot.getString("topic_img");


                                    showDoctor c = new showDoctor(name,id);
                                    topic_items.add(c);

                                    recyclerDoc.setLayoutManager(layoutManager);
                                    recyclerDoc.setHasFixedSize(true);
                                    recyclerDoc.setAdapter(adapterDoc);
                                    ;
                                    adapterDoc.notifyDataSetChanged();
                                    Log.e("get", topic_items.toString());

                                }
                            }
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("get", "get failed ");


                    }
                });
    }

    public void Delete(final showDoctor showdoctor) {

            db.collection("DoctorTopic").document(showdoctor.getId())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e("zzz", "deleted");
                            topic_items.remove(showdoctor);
                            Toast.makeText(DoctorHome.this, " Removed Successfully", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("zzz", "fail");
                        }
                    });
        }


    //option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTopic:
                startActivity(new Intent(DoctorHome.this, AddTopicsScreen.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(DoctorHome.this, DoctorProfile.class));
                return true;

            case R.id.home:
                startActivity(new Intent(DoctorHome.this, DoctorHome.class));
                return true;

            case R.id.chat:
                Toast.makeText(this, "chat", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position, String id) {
        Delete(topic_items.get(position));

    }
}