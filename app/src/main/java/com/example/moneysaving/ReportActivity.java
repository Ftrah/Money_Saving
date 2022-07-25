package com.example.moneysaving;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.DatePicker;

import com.example.moneysaving.adapter.TabunganListAdapter;
import com.example.moneysaving.database.TabunganDatabase;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Integer totalmoney;
    TabunganListAdapter adapter;
    TabunganDatabase DB;
    ArrayList<String> id, judul, deskripsi, kategori, total, tanggal;
    DatePicker datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        DB = new TabunganDatabase(ReportActivity.this);

        id = new ArrayList<>();
        judul = new ArrayList<>();
        deskripsi = new ArrayList<>();
        kategori = new ArrayList<>();
        total = new ArrayList<>();
        tanggal = new ArrayList<>();

        recyclerView = findViewById(R.id.list_item_report);

        datepicker = (DatePicker) findViewById(R.id.date_picker);
        datepicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String date = year + "-" + monthOfYear + "-" + dayOfMonth;
                renderAdapter(date);
            }
        });
    }

    void renderAdapter(String date){
        storeDataInArrays(date);

        Rupiah rupiah = new Rupiah(totalmoney);
//        total_view.setText(rupiah.getRupiah());

        adapter = new TabunganListAdapter(ReportActivity.this,this, id, judul, total, deskripsi, kategori, tanggal);
        recyclerView.removeAllViewsInLayout();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReportActivity.this));
    }

    void storeDataInArrays(String date){
        totalmoney = 0;

        id.clear();
        judul.clear();
        total.clear();
        deskripsi.clear();
        kategori.clear();
        tanggal.clear();



        Cursor cursor = DB.readDataDate(date);
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