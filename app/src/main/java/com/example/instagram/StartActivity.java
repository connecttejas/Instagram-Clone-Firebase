package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private ImageView mIconImage;
    private LinearLayout mLinearLayout;
    private ImageView mTextImage;
    private Button mLoginButton;
    private Button mRegisterButton;


    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mIconImage = findViewById(R.id.imageViewIconImage);
        mLinearLayout  = findViewById(R.id.linearLayout);
        mTextImage = findViewById(R.id.imageViewTextImage);
        mLoginButton = findViewById(R.id.login);
        mRegisterButton = findViewById(R.id.register);

        mLinearLayout.animate().alpha(0f).setDuration(1);

        TranslateAnimation translateAnimation =new TranslateAnimation(0,0,0,-1500);
        translateAnimation.setDuration(1500);
        translateAnimation.setFillAfter(false);
        translateAnimation.setAnimationListener(new MyAnimationListner());

        mIconImage.setAnimation(translateAnimation);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(StartActivity.this , MainActivity.class));
            finish();
        }
    }

    private class MyAnimationListner implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mIconImage.clearAnimation();
            mIconImage.setVisibility(View.GONE);
            mLinearLayout.animate().alpha(1f).setDuration(1000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
