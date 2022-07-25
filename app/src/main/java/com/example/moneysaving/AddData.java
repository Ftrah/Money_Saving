package com.example.moneysaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import com.example.moneysaving.database.TabunganDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddData extends AppCompatActivity {
    EditText text_judul, text_total, text_deskripsi;
    FloatingActionButton save_button;
    String kategori;
    DatePicker tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        text_judul = findViewById(R.id.text_judul);
        text_total = findViewById(R.id.text_total);
        text_deskripsi = findViewById(R.id.text_deskripsi);
        save_button = findViewById(R.id.button_save);
        tanggal = findViewById(R.id.date_picker);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabunganDatabase DB = new TabunganDatabase(AddData.this);


                Integer day = tanggal.getDayOfMonth();
                Integer month = (tanggal.getMonth() + 1);
                Integer year = tanggal.getYear();
                String tanggalBaru = year + "-" + month + "-" + day;

                DB.addData(
                    text_judul.getText().toString().trim(),
                    Integer.valueOf(text_total.getText().toString().trim()),
                    text_deskripsi.getText().toString().trim(),
                    kategori, tanggalBaru
                );

                Intent intent = new Intent(AddData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_pemasukan:
                if (checked)
                    kategori = "pemasukan";
                    break;
            case R.id.radio_pengeluaran:
                if (checked)
                    kategori = "pengeluaran";
                    break;
        }
    }
}