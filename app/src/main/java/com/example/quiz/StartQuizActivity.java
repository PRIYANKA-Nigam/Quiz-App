package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StartQuizActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        drawerLayout=findViewById(R.id.idraw);
        button=findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),QuizSelectActivity.class));
            }
        });
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
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) { if (drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START); } }
    public void ClickHome(View view){ QuizSelectActivity.redirectActivity(this,QuizSelectActivity.class); }
    public void ClickInstructions(View view){recreate();}
    public void ClickHistory(View view){QuizSelectActivity.redirectActivity(this,HistoryActivity.class);}
    public void ClickDark(View view){QuizSelectActivity.redirectActivity(this,DarkModeActivity.class);}
    public void ClickLogout(View view){
        MainActivity.logout(this);
    }
}