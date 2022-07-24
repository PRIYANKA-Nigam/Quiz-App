package com.example.quiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
private Context context;
List<Modal> list;

    public RecyclerViewAdapter(Context context, List<Modal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_row,parent,false);
        final MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.t1.setText( list.get(position).getT1());
        holder.t2.setText(list.get(position).getT2());
        holder.t3.setText(list.get(position).getT3());
        holder.t4.setText(list.get(position).getT4());
        holder.t5.setText(list.get(position).getT5());
        SharedPreferences sh =context.getSharedPreferences("chart", Context.MODE_PRIVATE);
        String s5=  sh.getString("pos",null);
        if (s5.equals("0"))
            holder.cardView.setCardBackgroundColor(Color.GREEN);
        else
            holder.cardView.setCardBackgroundColor(Color.RED);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
TextView t1,t2,t3,t4,t5;
CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.textView15);
            t2=itemView.findViewById(R.id.textView17);
            t3=itemView.findViewById(R.id.textView18);
            t4=itemView.findViewById(R.id.textView23);
            t5=itemView.findViewById(R.id.textView24);
            cardView=itemView.findViewById(R.id.cd);
        }
    }
}
