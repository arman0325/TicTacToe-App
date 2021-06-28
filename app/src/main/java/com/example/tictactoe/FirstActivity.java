package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    Animation topAnim,topAnim2,bottomAinm;
    ImageView image1, image2;
    TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cover);


        //Animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        topAnim2= AnimationUtils.loadAnimation(this,R.anim.top_animation2);
        bottomAinm = AnimationUtils.loadAnimation(this,R.anim.cover_text_animation);
        image1 = (ImageView) findViewById(R.id.coverImage);
        image2 = (ImageView) findViewById(R.id.coverImage2);
        textView = (TextView) findViewById(R.id.textViewLogo);
        image1.setAnimation(topAnim);
        image2.setAnimation((topAnim2));
        textView.setAnimation(bottomAinm);


        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);


    }


}
