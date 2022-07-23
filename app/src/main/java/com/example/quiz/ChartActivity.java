package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    PieChart pieChart; ImageView imageView;
    ArrayList<Integer> ac;
    ArrayList<String> bc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        imageView=findViewById(R.id.imageView2);
        Intent intent = getIntent();
        String a = intent.getStringExtra("c");
        String b = intent.getStringExtra("w");
        int pos=intent.getIntExtra("pos",0);
        Image image=new Image(this);
        imageView.setImageResource(image.image[pos]);
        pieChart = findViewById(R.id.pie);
        ac =new ArrayList<>();
        bc=new ArrayList<>();
        ac.add(Integer.parseInt(a));
        ac.add(Integer.parseInt(b));
        bc.add("correct");
        bc.add("wrong");
        pieset();

    }

    private void pieset() {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        ArrayList<String> x = new ArrayList<>();
        for (int i = 0; i < ac.size(); i++) {
            pieEntries.add(new PieEntry(ac.get(i)));
        }
        for (int i = 0; i < bc.size(); i++) {
            x.add(bc.get(i));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Test Result");
        ArrayList<Integer> c = new ArrayList<>();
        c.add(Color.GREEN);
        c.add(Color.RED);
        c.add(Color.BLACK);
        pieDataSet.setColors(c);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        PieData ppp = new PieData(pieDataSet);
        pieChart.setData(ppp);
        pieChart.invalidate();
    }
}