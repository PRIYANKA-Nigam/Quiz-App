package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class TimesUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_up);
        Intent intent=getIntent();
        int c= intent.getIntExtra("correct",0);
        int w =intent.getIntExtra("wrong",0);
        int s =intent.getIntExtra("skip",0);
        int l=intent.getIntExtra("len",0);
        SharedPreferences sharedPreferences=getSharedPreferences("times", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("ct", String.valueOf(c)).putString("wt", String.valueOf(w)).putString("st", String.valueOf(s))
                .putString("len", String.valueOf(l)).apply();
    }

    public void timesUp(View view) {
        Intent intent =new Intent(this,ResultActivity.class);
        startActivity(intent);
        finish();
    }
}