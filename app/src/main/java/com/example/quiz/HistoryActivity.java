package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HistoryActivity extends AppCompatActivity {
    DrawerLayout drawerLayout; String s1,s2,s3,s4,s5;
   static ListView listView; String c_val="";
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> userSelection=new ArrayList<>();
    String fixed="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = findViewById(R.id.ll);
        drawerLayout = findViewById(R.id.hdraw);
        try {
            ArrayList<String> ss = getIntent().getStringArrayListExtra("quote");
            arrayList.addAll(ss);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    for (int it=0;it<arrayList.size();it++){
                        if (i==it){
                          //  listView.getChildAt(it).setBackgroundColor(Color.BLUE);  or
                            view.setBackgroundColor(Color.BLUE);
                        }else {
                            listView.getChildAt(it).setBackgroundColor(Color.WHITE);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                AlertDialog.Builder a = new AlertDialog.Builder(HistoryActivity.this);
                a.setMessage("do you want to Share this Report ?....").setCancelable(false)
                        .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int in) {
                                String s = arrayList.get(i);
                                sharedLogic(s);
                            }
                        }).setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      dialogInterface.cancel();

                    }
                });
                AlertDialog alert = a.create();
                alert.setTitle("Share Result Report ");
                alert.show();
            }

        });
        try {
            loadData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void removeItem(List<String> list){
        for (String i : list){
            arrayList.remove(i);
        }
    }
    public void shareItem(List<String> list){
        String select="";
        for (String s : list) {
            select+=s+"\n";
        }
        sharedLogic(select);

    }
    private void sharedLogic(String s) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Quiz Report : " + s);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
        Toast.makeText(getApplicationContext(), "Sharing Result ...", Toast.LENGTH_SHORT).show();
        finish();
    }

private void loadData() {
    SharedPreferences sh = getSharedPreferences("religious", MODE_PRIVATE);
    Set<String> set = sh.getStringSet("event", new HashSet<String>());
    for (String i : set) {
        arrayList.add(i);
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                SharedPreferences sh=getApplicationContext().getSharedPreferences("religious",MODE_PRIVATE);
                HashSet<String> set=new HashSet<>(arrayList);
                sh.edit().putStringSet("event",set).apply();
                Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    AbsListView.MultiChoiceModeListener modeListener =new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
            if (userSelection.contains(arrayList.get(i))){
                userSelection.remove(arrayList.get(i));
            }else {
                userSelection.add(arrayList.get(i));
            }
            actionMode.setTitle(userSelection.size() +" items selected...");
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater menuInflater =actionMode.getMenuInflater();
            menuInflater.inflate(R.menu.delete_share,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.delete:
                    removeItem(userSelection);
                    arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sh = getApplicationContext().getSharedPreferences("religious", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet<>(arrayList);
                    sh.edit().putStringSet("event", set).apply();
                    Toast.makeText(getApplicationContext(), "Deleted this Quote ...", Toast.LENGTH_SHORT).show();
                    actionMode.finish();
                    return true;
                case R.id.share:
                    shareItem(userSelection);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };
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
    public void ClickDark(View view){MainActivity.redirectActivity(this,DarkModeActivity.class);}
    public void ClickLogout(View view){
        MainActivity.logout(this);
    }
}