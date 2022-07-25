package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter adapter;
    List<Modal> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        drawerLayout=findViewById(R.id.hdraw);
       try {
            SharedPreferences sh = getSharedPreferences("chart", Context.MODE_PRIVATE);
            String s1 = sh.getString("right", null);
            String s2 = sh.getString("wrong", null);
            String s3 = sh.getString("skip", null);
            String s4 = sh.getString("perc", null);
            String s5 = sh.getString("pos", null);
            if (s5.equals("0"))
                s5 = "pass";
            else
                s5 = "fail";

        list=new ArrayList<>();
        layoutManager=new LinearLayoutManager(this);
        Image image=new Image(this);
        recyclerView=findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecyclerViewAdapter(this,list);
        list.add(new Modal("Correct : "+s1,"Wrong : "+s2,"Skipped : "+s3,"Percentage : "+s4,"Status : "+s5));
        recyclerView.setAdapter(adapter);
      //  recyclerView.invalidate(); or
        adapter.notifyDataSetChanged();
       } catch (NullPointerException e){
           e.printStackTrace();
       }

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
    public void ClickHome(View view){ MainActivity.redirectActivity(this,MainActivity.class); }
    public void ClickInstructions(View view){MainActivity.redirectActivity(this,StartQuizActivity.class);}
    public void ClickHistory(View view){recreate();}
    public void ClickLogout(View view){
        MainActivity.logout(this);
    }
}