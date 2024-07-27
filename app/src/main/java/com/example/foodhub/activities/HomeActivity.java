package com.example.foodhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodhub.MainActivity;
import com.example.foodhub.R;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        if(auth.getCurrentUser()!=null){
            progressBar.setVisibility(View.VISIBLE);

            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            Toast.makeText(this, "please wait you are already logged in", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }



    public void login(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }
    public void registration(View view) {
        startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
    }
}