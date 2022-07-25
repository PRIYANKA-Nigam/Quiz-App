package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class ResultActivity extends AppCompatActivity {
TextView textView;
ImageView imageView;int flag=0;
    Button b1,b2,b3; static  int percentage=0;
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
                .putString("skip", String.valueOf(s)).putString("perc", String.valueOf(p)).putString("pos", String.valueOf(flag)).apply();

        Toast.makeText(this,"Your Result is saved .",Toast.LENGTH_SHORT).show();


    }

    public void exit(View view) {
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