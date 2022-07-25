package com.example.moneysaving.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneysaving.R;
import com.example.moneysaving.Rupiah;

import java.util.ArrayList;

public class TabunganListAdapter extends RecyclerView.Adapter<TabunganListAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList id, title, total, deskripsi, kategori, tanggal;

    private String colorPrimaryOp = "#cbefe3";
    private String colorPrimary = "#00b074";
    private String colorDangerOp = "#ffdad7";
    private String colorDanger = "#f14c3e";

    public TabunganListAdapter(
            Activity activity,
            Context context,
            ArrayList id,
            ArrayList title,
            ArrayList total,
            ArrayList deskripsi,
            ArrayList kategori,
            ArrayList tanggal){

        this.id = id;
        this.activity = activity;
        this.context = context;
        this.title = title;
        this.total = total;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.tanggal = tanggal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Rupiah rupiah = new Rupiah(Integer.parseInt(String.valueOf(total.get(position))));

        holder.view_judul.setText(String.valueOf(title.get(position)));
        holder.view_text.setText(rupiah.getRupiah());
        holder.view_tanggal.setText(String.valueOf(tanggal.get(position)));

        if(String.valueOf(kategori.get(position)).equals("pemasukan")){
            holder.bg_color_list.setBackgroundColor(Color.parseColor(colorPrimaryOp));
            holder.icon_list.setColorFilter(Color.parseColor(colorPrimary));
        } else {
            holder.icon_list.setColorFilter(Color.parseColor(colorDanger));
            holder.icon_list.setImageResource(R.drawable.ic_baseline_trending_down_24);
            holder.bg_color_list.setBackgroundColor(Color.parseColor(colorDangerOp));
        }

        //Recyclerview onClickListener

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView view_judul, view_text, view_tanggal;
        ImageView icon_list;
        LinearLayout bg_color_list;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view_judul = itemView.findViewById(R.id.view_judul);
            view_text = itemView.findViewById(R.id.view_total);
            view_tanggal = itemView.findViewById(R.id.view_tanggal);
            bg_color_list = itemView.findViewById(R.id.bg_color_list);
            icon_list = itemView.findViewById(R.id.icon_list);


            //Animate Recyclerview
            //Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            //mainLayout.setAnimation(translate_anim);
        }

    }
}
