package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class BarActivity extends AppCompatActivity {
BarChart barChart; ImageView imageView;
    ArrayList<Integer> ac;
    ArrayList<String> bc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        imageView=findViewById(R.id.imageView2);
        Intent intent=getIntent();
        String a=intent.getStringExtra("c");
        String b=intent.getStringExtra("w");
        int pos=intent.getIntExtra("pos",0);
        Image image=new Image(this);
        imageView.setImageResource(image.image[pos]);
        barChart=findViewById(R.id.bar);
        ac =new ArrayList<>();
        bc=new ArrayList<>();
        ac.add(Integer.parseInt(a));
        ac.add(Integer.parseInt(b));
        bc.add("correct");
        bc.add("wrong");
        BarDataSet bardataSet=new BarDataSet(datavalues(),"Test Result");
        BarData barData=new BarData();
        barData.addDataSet(bardataSet);
        barChart.setData(barData);
        barChart.invalidate();
        bardataSet.setColor(Color.RED);
        bardataSet.setValueTextColor(Color.BLACK);
        bardataSet.setValueTextSize(50f);


    }

    private List<BarEntry> datavalues() {
        ArrayList<BarEntry> datavals=new ArrayList<>();
        for(int i=0;i<ac.size();i++)
            datavals.add(new BarEntry(i,ac.get(i)));
        return datavals;
    }
}