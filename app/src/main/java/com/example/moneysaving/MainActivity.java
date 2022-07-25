package com.example.moneysaving;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.moneysaving.adapter.TabunganListAdapter;
import com.example.moneysaving.database.TabunganDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addButton;
    FloatingActionButton reportButton;
    TabunganDatabase DB;
    ArrayList<String> id, judul, deskripsi, kategori, total, tanggal;
    TabunganListAdapter adapter;
    RecyclerView recyclerView;
    Integer totalmoney;
    TextView total_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new TabunganDatabase(MainActivity.this);

        id = new ArrayList<>();
        judul = new ArrayList<>();
        deskripsi = new ArrayList<>();
        kategori = new ArrayList<>();
        total = new ArrayList<>();
        tanggal = new ArrayList<>();

        recyclerView = findViewById(R.id.list_item_main);
        addButton = findViewById(R.id.button_add);
        reportButton = findViewById(R.id.button_report);
        total_view = findViewById(R.id.total_view);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddData.class);
                startActivity(intent);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        renderAdapter();
    }

    void renderAdapter(){
        storeDataInArrays();

        Rupiah rupiah = new Rupiah(totalmoney);
        total_view.setText(rupiah.getRupiah());

        adapter = new TabunganListAdapter(MainActivity.this,this, id, judul, total, deskripsi, kategori, tanggal);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void storeDataInArrays(){
        totalmoney = 0;
        Cursor cursor = DB.readData();
        if(cursor.getCount() == 0){
//
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                judul.add(cursor.getString(1));
                total.add(cursor.getString(2));
                deskripsi.add(cursor.getString(3));
                kategori.add(cursor.getString(4));
                tanggal.add(cursor.getString(5));

                if(cursor.getString(4).equals("pemasukan")){
                    totalmoney += cursor.getInt(2);
                } else {
                    totalmoney -= cursor.getInt(2);
                }
            }
        }
    }
}