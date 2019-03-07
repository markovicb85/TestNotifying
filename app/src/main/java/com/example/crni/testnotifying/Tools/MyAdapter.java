package com.example.crni.testnotifying.Tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crni.testnotifying.Data.MyAlarm;
import com.example.crni.testnotifying.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<MyAlarm> myAlarms;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewTitle;
        private TextView textViewMsg;


        public MyViewHolder(View v) {
            super(v);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textView);
            textViewMsg = itemView.findViewById(R.id.textView2);
        }
    }

    public MyAdapter(ArrayList<MyAlarm> data) {
        myAlarms = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_alarm, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyAlarm currentItem = myAlarms.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textViewTitle.setText(currentItem.getText1());
        holder.textViewMsg.setText(currentItem.getText2());
        holder.itemView.setTag(currentItem.getId());
    }

    @Override
    public int getItemCount() {
        return myAlarms.size();
    }

}
