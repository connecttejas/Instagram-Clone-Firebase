package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.instagram.fragments.HomeFragment;
import com.example.instagram.fragments.NotificationFragment;
import com.example.instagram.fragments.ProfileFragment;
import com.example.instagram.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);



       bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()) {
                   case R.id.nav_home:
                       selectorFragment = new HomeFragment();

                       break;
                   case R.id.nav_search:
                       selectorFragment = new SearchFragment();
                       break;
                   case R.id.nav_add:
                       startActivity(new Intent(MainActivity.this, PostActivity.class));
                       break;
                   case R.id.nav_fav:
                       selectorFragment = new NotificationFragment();
                       break;
                   case R.id.person:
                       selectorFragment = new ProfileFragment();
                       break;
               }
               if (selectorFragment !=null){
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
               }
               return true;
           }
       });

       Bundle intent = getIntent().getExtras();
       if(intent!=null){
           String profileId = intent.getString("publisherId");
           getSharedPreferences("PROFILE",MODE_PRIVATE).edit().putString("profileId",profileId).apply();
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
           bottomNavigationView.setSelectedItemId(R.id.person);
       }else {

           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
       }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this,StartActivity.class));
            finish();
        }
    }
}
