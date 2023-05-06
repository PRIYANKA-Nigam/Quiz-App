package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
TextView textView,textView2,textView3,textView4; List<Questions> list;
static int len =0;String flag="",file="";int delay=0;
int count=0;static Timer timer;
Button b1,b2,b3,b4; static int correct =0; static int  c=0,w=0,s=0;
    public static void logout(final HistoryActivity historyActivity) {
        dialog(historyActivity);
    }
    public static void logout(final QuizSelectActivity historyActivity) {
        dialog(historyActivity);
    }
    private static void dialog(Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are You Sure You Want to Logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)  @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity(); System.exit(0); }});
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); }});
        builder.show();
    }

    public static void logout(final StartQuizActivity mainActivity3) {
       dialog(mainActivity3);
    }
    public static void logout(final DarkModeActivity darkModeActivity) {
        dialog(darkModeActivity);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=(DrawerLayout)findViewById(R.id.mdraw);
        flag=getIntent().getStringExtra("flag");
        switch (flag){
            case "c":
                delay = 150000;file="c_file.json"; break;
            case "cp":
                delay = 150000;file="cplusplus.json";break;
            case "j":
                delay = 150000;file="java.json";break;
            case "jd":
                delay = 180000;file="jdbc.json";break;
            case "s":
                delay = 180000;file="spring.json";break;
            case "g":
                delay = 180000;file="git.json";break;
            case "a":
                delay = 180000;file="android.json";break;
            case "m":
                delay = 180000;file="microservice.json";break;
            case "co":
                delay = 180000;file="container_VM.json";break;
            case "je":
                delay = 180000;file="jenkins.json";break;
            case "d":
                delay = 180000;file="docker.json";break;
            case "k":
                delay = 180000;file="Kubernetes.json";break;
            case "r":
                delay = 180000;file="rest_api.json";break;
            case "p":
                delay = 600000;file="phpDoc.json";break;
            case "az":
                delay = 900000;file="azure.json";break;
            case "js":
                delay = 900000;file="jsp.json";break;
        }
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
                        intent.putExtra("flag",flag);
                        startActivity(intent);
                        finish();
                    }

                },delay);
        }

        });
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        LayoutInflater inflater=getLayoutInflater();
        View view1=inflater.inflate(R.layout.toast_correct,null);
        TextView t1=(TextView) view1.findViewById(R.id.textView1);
        View view2=inflater.inflate(R.layout.toast_wrong,null);
        TextView t2=(TextView) view2.findViewById(R.id.textView2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { textView4.setText(""+correct);

              if (list.get(correct).getAnswer1().equals(list.get(correct).getCorrect())) {
                  c++;
                  t1.setText("Correct !!!");
                  Toast to1=new Toast(MainActivity.this); to1.setView(view1);
                  to1.setGravity(Gravity.LEFT |Gravity.TOP,150,150); to1.show();
              }else {
                  w++; t2.setText("Wrong , Correct Answer is -"+list.get(correct).getCorrect());
                  Toast to2=new Toast(MainActivity.this); to2.setView(view2);
                  to2.setGravity(Gravity.LEFT |Gravity.TOP,150,150); to2.show();
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
                    intent.putExtra("flag",flag);
                    startActivity(intent);
                    finish();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { textView4.setText(""+correct);
                if (list.get(correct).getAnswer2().equals(list.get(correct).getCorrect())) {
                    c++; t1.setText("Correct !!!");
                    Toast to1=new Toast(MainActivity.this); to1.setView(view1);
                    to1.setGravity(Gravity.LEFT |Gravity.TOP,150,150); to1.show();
                }else {
                    w++; t2.setText("Wrong , Correct Answer is -"+list.get(correct).getCorrect());
                    Toast to2=new Toast(MainActivity.this); to2.setView(view2);
                    to2.setGravity(Gravity.LEFT |Gravity.TOP,150,150); to2.show();
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
                    intent.putExtra("flag",flag);
                    startActivity(intent);
                    finish();
                }

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { textView4.setText(""+correct);
                if (list.get(correct).getAnswer3().equals(list.get(correct).getCorrect())) {
                    c++; t1.setText("Correct !!!");
                    Toast to1=new Toast(MainActivity.this); to1.setView(view1);
                    to1.setGravity(Gravity.LEFT |Gravity.TOP,150,150); to1.show();
                }else {
                    w++; t2.setText("Wrong , Correct Answer is -"+list.get(correct).getCorrect());
                    Toast to2=new Toast(MainActivity.this); to2.setView(view2);
                    to2.setGravity(Gravity.LEFT |Gravity.TOP,150,150); to2.show();
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
                    intent.putExtra("flag",flag);
                    startActivity(intent);
                   finish();
                }

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { textView4.setText(""+correct);
                if (list.get(correct).getAnswer4().equals(list.get(correct).getCorrect())) {
                    c++; t1.setText("Correct !!!");
                    Toast to1=new Toast(MainActivity.this); to1.setView(view1);
                    to1.setGravity(Gravity.LEFT |Gravity.TOP,150,150); to1.show();
                }else {
                    w++; t2.setText("Wrong , Correct Answer is -"+list.get(correct).getCorrect());
                    Toast to2=new Toast(MainActivity.this); to2.setView(view2);
                    to2.setGravity(Gravity.LEFT |Gravity.TOP,150,150); to2.show();
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
                    intent.putExtra("flag",flag);
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
       String jsonstr =LoadJsonFromAsset(file);
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
            intent.putExtra("flag",flag);
            startActivity(intent);
             finish();
        }
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
    public void ClickMenu(View view){ openDrawer(drawerLayout); }
    public static void openDrawer(DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) { if (drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START); } }
    public void ClickHome(View view){ recreate(); }
    public void ClickInstructions(View view){redirectActivity(this,StartQuizActivity.class);}
    public void ClickHistory(View view){redirectActivity(this,HistoryActivity.class);}
    public void ClickDark(View view){redirectActivity(this,DarkModeActivity.class);}
    public void ClickLogout(View view){
        logout(this);
    }

    public static void logout(final MainActivity mainActivity) {
       dialog(mainActivity);
    }
    public static void redirectActivity(Activity activity, Class aclass) { Intent intent=new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); activity.startActivity(intent); } @Override
    protected void onPause() { super.onPause(); closeDrawer(drawerLayout); }
}