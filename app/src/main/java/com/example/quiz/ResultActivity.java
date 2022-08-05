package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {
TextView textView; ArrayList<String> list=new ArrayList<>();
ImageView imageView;int flag=0; String news="",col="";
    Button b1,b2,b3; static  int percentage=0; String fl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = findViewById(R.id.textView6);
        imageView = findViewById(R.id.imageView4);
        b1=findViewById(R.id.button5);
        b2=findViewById(R.id.button6);
        b3=findViewById(R.id.button8);

        SharedPreferences sharedPreferences=getSharedPreferences("times", Context.MODE_PRIVATE);
        String ct=sharedPreferences.getString("ct",null);
        String wt=sharedPreferences.getString("wt",null);
        String st=sharedPreferences.getString("st",null);
        String length=sharedPreferences.getString("len",null);
         fl=sharedPreferences.getString("flag",null);
       int  c=Integer.parseInt(ct);int w=Integer.parseInt(wt);int s=Integer.parseInt(st); int len=Integer.parseInt(length);
        b1.setText("Correct :"+c);
        b2.setText("Wrong :"+w); b3.setText("Skipped :"+s);
        int p =((c*100)/len);
        textView.setText(" "+p+"%");
        if (p>50) {
            imageView.setImageResource(R.drawable.pass);
            flag=0;
        }
        else {
            imageView.setImageResource(R.drawable.fail);
            flag=1;
        }
        SharedPreferences sh =getSharedPreferences("chart", Context.MODE_PRIVATE);
        sh.edit().putString("right", String.valueOf(c)).putString("wrong", String.valueOf(w))
                .putString("skip", String.valueOf(s)).putString("perc", String.valueOf(p)).putString("pos", String.valueOf(flag))
                .putString("colo",col).apply();

        Toast.makeText(this,"Your Result is saved .",Toast.LENGTH_SHORT).show();


    }

    public void exit(View view) {

        SharedPreferences sh =getSharedPreferences("chart", Context.MODE_PRIVATE);
        String s1=  sh.getString("right",null);
        String s2=  sh.getString("wrong",null);
        String s3=  sh.getString("skip",null);
        String perc=sh.getString("perc",null);
        String flag=sh.getString("pos",null);
        String type="";
        if (fl.equals("c"))
            type="C Programming";
        if (fl.equals("cp"))
            type="C++ Programming";
        if (fl.equals("j"))
            type="Java Programming";
        if (fl.equals("jd"))
            type="JDBC";
        if (fl.equals("s"))
            type="Spring Framework";
        if (fl.equals("g"))
            type="Git SCM Tool";
        if (fl.equals("a"))
            type="Android Development";
        Calendar calendar =Calendar.getInstance();
//        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        String curDate=format.format(calendar.getTime());
        String curDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        if (flag.equals("0"))
        news="Correct: "+s1+"\n"+"-------------------- \n"+"Incorrect: "+s2+"\n"+"--------------------- \n"+"Skipped: "+s3+"\n"+"------------------------ \n"+"Percentage: "
                +perc+"%"+"\n"+"---------------------\n"+"Status: Pass\n"+"-------------------------\n"+"Subject: "+type+"             \n\n("+curDate+")";
        else
            news="Correct: "+s1+"\n"+"---------- \n"+"Incorrect: "+s2+"\n"+"----------- \n"+"Skipped: "+s3+"\n"+"------------- \n"+"Percentage: "
                    +perc+"%"+"\n"+"--------------------\n"+"Status: Fail\n"+"-------------------------\n"+"Subject: "+type+"                \n\n("+curDate+")";
        list.add(news);
        Intent intent=new Intent(getApplicationContext(),HistoryActivity.class);
        intent.putStringArrayListExtra("quote", list);
        Toast.makeText(getApplicationContext(),"Report passed to Result History Page ...",Toast.LENGTH_SHORT).show();
        startActivity(intent);
        System.exit(0);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.chart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.graph) {
            SharedPreferences sh =getSharedPreferences("chart", Context.MODE_PRIVATE);
          String s1=  sh.getString("right",null);
          String s2=  sh.getString("wrong",null);
            String s3=  sh.getString("skip",null);
          Intent intent=new Intent(getApplicationContext(),BarActivity.class);
          intent.putExtra("c",s1);
          intent.putExtra("w",s2);
            intent.putExtra("s",s3);
          intent.putExtra("pos",flag);
          startActivity(intent);
        }
        else   if (item.getItemId()==R.id.chart) {
            SharedPreferences sh =getSharedPreferences("chart", Context.MODE_PRIVATE);
            String s1=  sh.getString("right",null);
            String s2=  sh.getString("wrong",null);
            String s3=  sh.getString("skip",null);
            Intent intent=new Intent(getApplicationContext(),ChartActivity.class);
            intent.putExtra("c",s1);
            intent.putExtra("w",s2);
            intent.putExtra("s",s3);
            intent.putExtra("pos",flag);
            startActivity(intent);
        }

        return false;
    }
}