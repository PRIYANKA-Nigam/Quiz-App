package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
TextView textView; Animation animation;
ImageView i1,i2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView=findViewById(R.id.textView2);
        i1=findViewById(R.id.imageView2);
        i2=findViewById(R.id.imageView3);
        animation   = AnimationUtils.loadAnimation(this,R.anim.splash);
        textView.startAnimation(animation);
        i1.startAnimation(animation);
        i2.startAnimation(animation);
        final Intent intent=new Intent(this,StartQuizActivity.class);
        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();

    }
}