package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
TextView textView,textView2,textView3; List<Questions> list;
int count=0;
Button b1,b2,b3,b4; int correct =0; static int  c=0,w=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.tt);
        textView2=findViewById(R.id.textView4);
        textView3=findViewById(R.id.textView5);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE); //SS NOT ALLOWED
        Thread t =new Thread(){
            @Override
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
            public void onClick(View view) {
                t.start();
                LoadQuestions();
                Collections.shuffle(list);
                setScreen(correct);
                if (count>60)
                    t.stop();
            }
        });
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (list.get(correct).getAnswer1().equals(list.get(correct).getCorrect())) {
                  c++;
                  Toast.makeText(MainActivity.this, "Correct !!!", Toast.LENGTH_SHORT).show();
              }else {
                  w++;
                  Toast.makeText(MainActivity.this, "Wrong , Correct Answer is -"+list.get(correct).getCorrect(), Toast.LENGTH_SHORT).show();
              }
                if (correct<list.size()-1){
                    correct++;setScreen(correct);
                }else {
                    Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("correct",c);
                    intent.putExtra("wrong",w);
                    startActivity(intent);
                   // finish();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(correct).getAnswer2().equals(list.get(correct).getCorrect())) {
                    c++;
                    Toast.makeText(MainActivity.this, "Correct !!!", Toast.LENGTH_SHORT).show();
                }else {
                    w++;
                    Toast.makeText(MainActivity.this, "Wrong , Correct Answer is -"+list.get(correct).getCorrect(), Toast.LENGTH_SHORT).show();
                }
                if (correct<list.size()-1){
                    correct++;setScreen(correct);
                }else {
                    Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("correct",c);
                    intent.putExtra("wrong",w);
                    startActivity(intent);
                  //  finish();
                }

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(correct).getAnswer3().equals(list.get(correct).getCorrect())) {
                    c++;
                    Toast.makeText(MainActivity.this, "Correct !!!", Toast.LENGTH_SHORT).show();
                }else {
                    w++;
                    Toast.makeText(MainActivity.this, "Wrong , Correct Answer is -"+list.get(correct).getCorrect(), Toast.LENGTH_SHORT).show();
                }
                if (correct<list.size()-1){
                    correct++;setScreen(correct);
                }else {
                    Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("correct",c);
                    intent.putExtra("wrong",w);
                    startActivity(intent);
                  // finish();
                }

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(correct).getAnswer4().equals(list.get(correct).getCorrect())) {
                    c++;
                    Toast.makeText(MainActivity.this, "Correct !!!", Toast.LENGTH_SHORT).show();
                }else {
                    w++;
                    Toast.makeText(MainActivity.this, "Wrong !!! , \n\n Correct Answer is -"+list.get(correct).getCorrect(), Toast.LENGTH_SHORT).show();
                }
                if (correct<list.size()-1){
                    correct++;setScreen(correct);
                }else {
                    Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("correct",c);
                    intent.putExtra("wrong",w);
                    startActivity(intent);
                   // finish();
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
}