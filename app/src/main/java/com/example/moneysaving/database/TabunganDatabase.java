package com.example.moneysaving.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TabunganDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "TabunganDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tabel_tabungan";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_JUDUL = "judul";
    private static final String COLUMN_DESKRIPSI = "deskripsi";
    private static final String COLUMN_JUMLAH = "jumlah";
    private static final String COLUMN_KATEGORI = "ketegori";
    private static final String COLUMN_TANGGAL = "tanggal";

    public TabunganDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_JUDUL + " TEXT, " +
                COLUMN_JUMLAH + " INTEGER, " +
                COLUMN_DESKRIPSI + " TEXT, " +
                COLUMN_KATEGORI + " TEXT, " +
                COLUMN_TANGGAL +" DATE);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(String judul, int jumlah, String deskripsi, String kategori, String tanggal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_JUDUL, judul);
        cv.put(COLUMN_JUMLAH, jumlah);
        cv.put(COLUMN_DESKRIPSI, deskripsi);
        cv.put(COLUMN_KATEGORI, kategori);
        cv.put(COLUMN_TANGGAL, tanggal);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readData(){
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readDataDate(String date){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE tanggal='" + date + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void editData(String row_id, String judul, int jumlah, String deskripsi, String kategori, String tanggal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_JUDUL, judul);
        cv.put(COLUMN_JUMLAH, jumlah);
        cv.put(COLUMN_DESKRIPSI, deskripsi);
        cv.put(COLUMN_KATEGORI, kategori);
        cv.put(COLUMN_TANGGAL, tanggal);

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

//    void deleteAllData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_NAME);
//    }
}
