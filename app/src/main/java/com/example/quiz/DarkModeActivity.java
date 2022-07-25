package com.example.quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Switch;

public class DarkModeActivity extends AppCompatActivity {
CardView cardView; Switch aSwitch; DrawerLayout drawerLayout;
private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref =new SharedPref(this);
//        if (sharedPref.loadDarkModeState()){
//            setTheme(R.style.SettingsDark);
//        }else {
//            setTheme(R.style.SettingsLight);
//
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dark_mode);
        drawerLayout=(DrawerLayout)findViewById(R.id.dadraw);
        cardView =findViewById(R.id.cdd);
        aSwitch=findViewById(R.id.swit);
        if (sharedPref.loadDarkModeState()){
            aSwitch.setChecked(true);
        }
        aSwitch.setClickable(false);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!aSwitch.isChecked()){
                    aSwitch.setChecked(true);
                    sharedPref.setDarkModeState(true);
                    restartApp();
                }else {
                    aSwitch.setChecked(false);
                    sharedPref.setDarkModeState(false);
                    restartApp();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void restartApp() {
        Intent intent=new Intent(getApplicationContext(),DarkModeActivity.class);
        startActivity(intent);
        finish();
    }
    public void ClickMenu(View view){ openDrawer(drawerLayout); }
    public static void openDrawer(DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) { if (drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START); } }
    public void ClickHome(View view){
        MainActivity.redirectActivity(this,MainActivity.class);
    }
    public void ClickInstructions(View view){MainActivity.redirectActivity(this,StartQuizActivity.class);}
    public void ClickHistory(View view){MainActivity.redirectActivity(this,HistoryActivity.class);}
    public void ClickDark(View view){recreate();}
    public void ClickLogout(View view){
        MainActivity.logout(this);
    }
}