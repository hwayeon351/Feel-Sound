package com.course.feelsound;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mysound, mytime;

        MyViewHolder(View view){
            super(view);
            mysound = view.findViewById(R.id.sound_name);
            mytime = view.findViewById(R.id.sound_time);
        }
    }

    private ArrayList<Record> sound_list;

    MyAdapter(ArrayList<Record> sounds){
        this.sound_list = sounds;
    }
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position){
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.mysound.setText(sound_list.get(position).getS_name());
        myViewHolder.mytime.setText(sound_list.get(position).getTime());

    }

    @Override
    public int getItemCount(){
        return sound_list.size();
    }
}
