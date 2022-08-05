package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class TestCompleteActivity extends AppCompatActivity {
    String f="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_complete);

        Intent intent=getIntent();
        int c= intent.getIntExtra("correct",0);
        int w =intent.getIntExtra("wrong",0);
        int s =intent.getIntExtra("skip",0);
        int l=intent.getIntExtra("len",0);
         f=intent.getStringExtra("flag");
        SharedPreferences sharedPreferences=getSharedPreferences("times", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("ct", String.valueOf(c)).putString("wt", String.valueOf(w)).putString("st", String.valueOf(s))
                .putString("len", String.valueOf(l)).putString("flag",f).apply();
    }

    public void thanks(View view) {
        MainActivity.timer.cancel();
        Intent intent =new Intent(this,ResultActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.dark_bright,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.dark:
                startActivity(new Intent(getApplicationContext(),DarkModeActivity.class));return  true;
            case R.id.bright:
                startActivity(new Intent(getApplicationContext(),BrightnessActivity.class));return  true;

        }
        return super.onOptionsItemSelected(item);
    }
}