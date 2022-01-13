package com.example.androidapp1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "marks_table";
    private static final String Col1 = "ID";
    private static final String Col2 = "Nev";
    private static final String Col3 = "Sum";
    private static final String Col4 = "Count";

    public Database(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + Col2 + " TEXT, " + Col3 + " INT, " + Col4 + " INT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String nev, int sum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col2, nev);
        contentValues.put(Col3, sum);

        boolean match = false;
        String dbNev = "";
        int dbSum = 1;
        int dbCount = 1;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT " + Col2 + ", " + Col3 + ", " + Col4 + " FROM " + TABLE_NAME + " WHERE " + Col2 + " = '" + nev + "'", null);
        while (cursor.moveToNext()) {
            String val = cursor.getString(0);
            int num = cursor.getInt(1);
            int count = cursor.getInt(2);
            if (val.equals(nev)) {
                match = true;
                dbNev = val;
                dbSum = num;
                dbCount = count;
                break;
            }
        }
        if (match) {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + Col3 + " = '" + (sum + dbSum) + "', " + Col4 + " = '" + (dbCount + 1) + "' WHERE " + Col2 + " = '" + dbNev + "'");
            return true;
        } else {
            contentValues.put(Col4, 1);
            long result = db.insert(TABLE_NAME, null, contentValues);

            return result != -1;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }
}
