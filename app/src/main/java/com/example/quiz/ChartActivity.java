package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        String c=intent.getStringExtra("s");
        int pos=intent.getIntExtra("pos",0);
        Image image=new Image(this);
        imageView.setImageResource(image.image[pos]);
        pieChart = findViewById(R.id.pie);
        ac =new ArrayList<>();
        bc=new ArrayList<>();
        ac.add(Integer.parseInt(a));
        ac.add(Integer.parseInt(b));  ac.add(Integer.parseInt(c));
        bc.add("correct");
        bc.add("wrong");   bc.add("skipped");
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
        c.add(Color.YELLOW);
        pieDataSet.setColors(c);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        PieData ppp = new PieData(pieDataSet);
        pieChart.setData(ppp);
        pieChart.invalidate();
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
}