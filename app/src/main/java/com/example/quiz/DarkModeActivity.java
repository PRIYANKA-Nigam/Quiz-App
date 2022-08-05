package com.example.quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class DarkModeActivity extends AppCompatActivity {
CardView cardView; Switch aSwitch; DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }else {
            setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dark_mode);
        drawerLayout=(DrawerLayout)findViewById(R.id.dadraw);
        cardView =findViewById(R.id.cdd);
        aSwitch=findViewById(R.id.swit);
       aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               if (isChecked){
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
               }else {
                   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
               }
           }
       });

    }

    public void ClickMenu(View view){ openDrawer(drawerLayout); }
    public static void openDrawer(DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) { if (drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START); } }
    public void ClickHome(View view){ QuizSelectActivity.redirectActivity(this,QuizSelectActivity.class);
    }
    public void ClickInstructions(View view){QuizSelectActivity.redirectActivity(this,StartQuizActivity.class);}
    public void ClickHistory(View view){QuizSelectActivity.redirectActivity(this,HistoryActivity.class);}
    public void ClickDark(View view){recreate();}
    public void ClickLogout(View view){
        QuizSelectActivity.logout(this);
    }
}