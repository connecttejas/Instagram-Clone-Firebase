package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private  Button mRegisterButton;
    private TextView mLoginTextview;
    private ProgressBar progressBar;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = findViewById(R.id.registerName);
        mUsername = findViewById(R.id.registerUsername);
        mEmail  = findViewById(R.id.registerEmail);
        mPassword = findViewById(R.id.registerPassword);
        mRegisterButton = findViewById(R.id.registerRegisterButton);
        mLoginTextview = findViewById(R.id.registerLoginText);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();





            mLoginTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });

            mRegisterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   String name = mName.getText().toString();
                   String username = mUsername.getText().toString();
                   String email = mEmail.getText().toString();
                   String password = mPassword.getText().toString();

                   if (TextUtils.isEmpty(name) || TextUtils.isEmpty(username)  || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                       Toast.makeText(RegisterActivity.this, "Fill all the credentials", Toast.LENGTH_SHORT).show();
                   }else if (password.length()<6){
                       Toast.makeText(RegisterActivity.this, "Lenght of Password should be greater than 6 digits", Toast.LENGTH_SHORT).show();
                   }else{
                       registerUsers(name,username,email,password);
                   }

                }
            });

    }

    private void registerUsers(final String name, final String username, final String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                HashMap<String ,Object> map = new HashMap<>();
                map.put("name",name);
                map.put("username",username);
                map.put("email",email);
                map.put("id",mAuth.getCurrentUser().getUid()  );
                map.put("bio","");
                map.put("imageUrl","default");
                progressBar.setVisibility(View.GONE);
                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RegisterActivity.this, "Update the profile " +
                                "for better expereince", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }



}
