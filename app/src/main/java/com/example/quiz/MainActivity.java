package com.example.quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity { DrawerLayout drawerLayout;
TextView textView,textView2,textView3,textView4; List<Questions> list;
static int len =0;
int count=0;static Timer timer;
Button b1,b2,b3,b4; static int correct =0; static int  c=0,w=0,s=0;
  //  private SharedPref sharedPref;
    public static void logout(final HistoryActivity historyActivity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(historyActivity);builder.setTitle("Logout");
        builder.setMessage("Are You Sure You Want to Logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)  @Override
            public void onClick(DialogInterface dialog, int which) {
               historyActivity.finishAffinity(); System.exit(0); }});
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); }});builder.show(); }
    public static void logout(final StartQuizActivity mainActivity3) {
        AlertDialog.Builder builder=new AlertDialog.Builder(mainActivity3);builder.setTitle("Logout");
        builder.setMessage("Are You Sure You Want to Logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)  @Override
            public void onClick(DialogInterface dialog, int which) {
                mainActivity3.finishAffinity(); System.exit(0); }});
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); }});builder.show(); }
    public static void logout(final DarkModeActivity darkModeActivity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(darkModeActivity);builder.setTitle("Logout");
        builder.setMessage("Are You Sure You Want to Logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)  @Override
            public void onClick(DialogInterface dialog, int which) {
                darkModeActivity.finishAffinity(); System.exit(0); }});
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); }});builder.show(); }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        sharedPref =new SharedPref(this);
//        if (sharedPref.loadDarkModeState()){
//            setTheme(R.style.SettingsDark);
//        }else {
//            setTheme(R.style.SettingsLight);
//
//        }
        drawerLayout=(DrawerLayout)findViewById(R.id.draw);
        textView=findViewById(R.id.tt);
        textView2=findViewById(R.id.textView4);
        textView3=findViewById(R.id.textView5);
        textView4=findViewById(R.id.textt);
        timer=new Timer();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE); //SS NOT ALLOWED
        Thread t =new Thread(){
            @Override                                   //Thread class is used only to update the count value & start loading the questions .
                                                       // t.stop was unable to stop the thread (it is deprecated)  hence used Timer inside the thread class which will
                                                        // stop the test as per the alloted time
                                                      //and we also cancelled the timer in the next activity. CountDownTimer can also be used in place of timer.
            public void run() {
                while (!isInterrupted()){
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count++;
                                textView2.setText(String.valueOf(count));

                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { c=0;w=0;s=0;
                t.start();
                LoadQuestions();
                Collections.shuffle(list);
                setScreen(correct);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, TimesUpActivity.class);
                        intent.putExtra("correct", c);
                        intent.putExtra("wrong", w);
                        intent.putExtra("skip", s);
                        intent.putExtra("len", len);
                        startActivity(intent);
                        finish();
                    }

                },30000);
        }

        });
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { textView4.setText(""+correct);
              if (list.get(correct).getAnswer1().equals(list.get(correct).getCorrect())) {
                  c++;
                  Toast.makeText(MainActivity.this, "Correct !!!", Toast.LENGTH_SHORT).show();
              }else {
                  w++;
                  Toast.makeText(MainActivity.this, "Wrong , Correct Answer is -"+list.get(correct).getCorrect(), Toast.LENGTH_SHORT).show();
              }
                if (correct<list.size()-1){
                    correct++; textView4.setText(""+correct);
                    setScreen(correct);
                }else {
                    Intent intent=new Intent(getApplicationContext(),TestCompleteActivity.class);
                    intent.putExtra("correct",c);
                    intent.putExtra("wrong",w);
                    intent.putExtra("skip",s);
                    intent.putExtra("len",len);
                    startActivity(intent);
                    finish();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { textView4.setText(""+correct);
                if (list.get(correct).getAnswer2().equals(list.get(correct).getCorrect())) {
                    c++;
                    Toast.makeText(MainActivity.this, "Correct !!!", Toast.LENGTH_SHORT).show();
                }else {
                    w++;
                    Toast.makeText(MainActivity.this, "Wrong , Correct Answer is -"+list.get(correct).getCorrect(), Toast.LENGTH_SHORT).show();
                }
                if (correct<list.size()-1){
                    correct++; textView4.setText(""+correct);
                    setScreen(correct);
                }else {
                    Intent intent=new Intent(getApplicationContext(),TestCompleteActivity.class);
                    intent.putExtra("correct",c);
                    intent.putExtra("wrong",w);
                    intent.putExtra("skip",s);
                    intent.putExtra("len",len);
                    startActivity(intent);
                    finish();
                }

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { textView4.setText(""+correct);
                if (list.get(correct).getAnswer3().equals(list.get(correct).getCorrect())) {
                    c++;
                    Toast.makeText(MainActivity.this, "Correct !!!", Toast.LENGTH_SHORT).show();
                }else {
                    w++;
                    Toast.makeText(MainActivity.this, "Wrong , Correct Answer is -"+list.get(correct).getCorrect(), Toast.LENGTH_SHORT).show();
                }
                if (correct<list.size()-1){
                    correct++; textView4.setText(""+correct);
                    setScreen(correct);
                }else {
                    Intent intent=new Intent(getApplicationContext(),TestCompleteActivity.class);
                    intent.putExtra("correct",c);
                    intent.putExtra("wrong",w);
                    intent.putExtra("skip",s);
                    intent.putExtra("len",len);
                    startActivity(intent);
                   finish();
                }

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { textView4.setText(""+correct);
                if (list.get(correct).getAnswer4().equals(list.get(correct).getCorrect())) {
                    c++;
                    Toast.makeText(MainActivity.this, "Correct !!!", Toast.LENGTH_SHORT).show();
                }else {
                    w++;
                    Toast.makeText(MainActivity.this, "Wrong !!! , \n\n Correct Answer is -"+list.get(correct).getCorrect(), Toast.LENGTH_SHORT).show();
                }
                if (correct<list.size()-1){
                    correct++; textView4.setText(""+correct);
                    setScreen(correct);
                }else {
                    Intent intent=new Intent(getApplicationContext(),TestCompleteActivity.class);
                    intent.putExtra("correct",c);
                    intent.putExtra("wrong",w);
                    intent.putExtra("skip",s);
                    intent.putExtra("len",len);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void setScreen(int num){
        textView.setText(list.get(num).getQuestion());
        b1.setText(list.get(num).getAnswer1());
        b2.setText(list.get(num).getAnswer2());
        b3.setText(list.get(num).getAnswer3());
        b4.setText(list.get(num).getAnswer4());


    }
    private void LoadQuestions(){
       list =new ArrayList<>();
       String jsonstr =LoadJsonFromAsset("new.json");
     try{
            JSONObject jsonObject = new JSONObject(jsonstr);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            len=jsonArray.length();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject question =jsonArray.getJSONObject(i);
                String quesString =question.getString("question");
                String ansString1 =question.getString("option1");
                String ansString2 =question.getString("option2");
                String ansString3 =question.getString("option3");
                String ansString4 =question.getString("option4");
                String correct =question.getString("answer");
                list.add(new Questions(quesString,ansString1,ansString2,ansString3,ansString4,correct));
            }
        }catch (JSONException e){
         e.printStackTrace();
     }

    }
    private String LoadJsonFromAsset(String file){
        String json ="";
        try{
            InputStream ls = getAssets().open(file);
            int size = ls.available();
            byte[] buffer = new byte[size];
            ls.read(buffer);
            ls.close();
            json=new String(buffer,"UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        return json;
    }

    public void skip(View view) { s++;
        if (correct<list.size()-1){
            correct++; textView4.setText(""+correct);
            setScreen(correct);
        }else {
            Intent intent=new Intent(getApplicationContext(),TestCompleteActivity.class);
            intent.putExtra("correct",c);
            intent.putExtra("wrong",w);
            intent.putExtra("skip",s);
            intent.putExtra("len",len);
            startActivity(intent);
             finish();
        }
    }
    public void ClickMenu(View view){ openDrawer(drawerLayout); }
    public static void openDrawer(DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) { if (drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START); } }
    public void ClickHome(View view){
        recreate();
    }
    public void ClickInstructions(View view){redirectActivity(this,StartQuizActivity.class);}
    public void ClickHistory(View view){redirectActivity(this,HistoryActivity.class);}
    public void ClickDark(View view){redirectActivity(this,DarkModeActivity.class);}
    public void ClickLogout(View view){
        logout(this);
    }

    public static void logout(final MainActivity mainActivity) { AlertDialog.Builder builder=new AlertDialog.Builder(mainActivity);
        builder.setTitle("Logout");builder.setMessage("Are You Sure You Want to Logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)  @Override
            public void onClick(DialogInterface dialog, int which) {
                mainActivity.finishAffinity();System.exit(0); }});
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() { @Override
        public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }}); builder.show(); }
    public static void redirectActivity(Activity activity, Class aclass) { Intent intent=new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); activity.startActivity(intent); } @Override
    protected void onPause() { super.onPause(); closeDrawer(drawerLayout); }


}