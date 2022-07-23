package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
TextView textView;
ImageView imageView;
    Button b1,b2; static  int percentage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = findViewById(R.id.textView6);
        imageView = findViewById(R.id.imageView4);
        b1=findViewById(R.id.button5);
        b2=findViewById(R.id.button6);
        Intent intent=getIntent();
        int c= intent.getIntExtra("correct",0);
        int w =intent.getIntExtra("wrong",0);
        b1.setText("Correct :"+c);
        b2.setText("Wrong :"+w);
        int p =((c*100)/(c+w));
      //  percentage=(c/(c+w)*100);
        textView.setText(" "+p);
        if (p>50)
            imageView.setImageResource(R.drawable.pass);
        else
            imageView.setImageResource(R.drawable.fail);
    }

    public void exit(View view) {
        System.exit(0);
    }
}