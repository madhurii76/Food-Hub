package com.example.foodhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodhub.R;
import com.example.foodhub.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    Button signUp;
    EditText name,email,password;
    TextView signIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        signUp=findViewById(R.id.reg_btn);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email_reg);
        password=findViewById(R.id.password_reg);
        signIn=findViewById(R.id.sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createUser();
               progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createUser() {
        String userName=name.getText().toString();
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length()<6){
            Toast.makeText(this, "Password Length must be greater than 6 letter", Toast.LENGTH_SHORT).show();
            return;
        }

            //Create User
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            UserModel userModel=new UserModel(userName,userEmail,userPassword,"","","");
                            String id=task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(RegistrationActivity.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Log.d("aaaaaaaaaaa", "onComplete: "+task.getException());
                            Toast.makeText(RegistrationActivity.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}