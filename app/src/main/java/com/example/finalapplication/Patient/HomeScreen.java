package com.example.finalapplication.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.finalapplication.Fragments.HomeFragment;
import com.example.finalapplication.Fragments.ProfileFragment;
import com.example.finalapplication.Fragments.chatFragment;
import com.example.finalapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeScreen extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
    @SuppressLint({"MissingInflatedId", "CommitTransaction"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment=new HomeFragment();
                        break;
                    case R.id.chat:
                        fragment=new chatFragment();
                        break;
                    case R.id.profile:
                        fragment=new ProfileFragment() ;
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();
                return true;
            }
        });
    }


}