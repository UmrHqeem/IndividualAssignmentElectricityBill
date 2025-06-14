package com.example.individualassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BillDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ElectricityBills.db";
    private static final int DATABASE_VERSION = 1;

    public BillDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE bills (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "month TEXT, " +
                "units REAL, " +
                "rebate INTEGER, " +
                "total REAL, " +
                "finalCost REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bills");
        onCreate(db);
    }

    public void insertBill(String month, double units, int rebate, double total, double finalCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("month", month);
        values.put("units", units);
        values.put("rebate", rebate);
        values.put("total", total);
        values.put("finalCost", finalCost);
        db.insert("bills", null, values);
    }

    public Cursor getAllBills() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM bills", null);
    }

    public Cursor getBillById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM bills WHERE id = ?", new String[]{String.valueOf(id)});
    }
}