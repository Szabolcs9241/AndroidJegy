package com.example.androidapp1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "marks_table";
    private static String Col1 = "ID";
    private static String Col2 = "Nev";
    private static String Col3 = "Jegy";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + Col2 + " TEXT," +  Col3 + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String nev, String jegy){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col2, nev);
        contentValues.put(Col3, jegy);

        Log.d(TAG, "addData: Adding " + nev + " to " + TABLE_NAME);

        boolean match = false;
        String value = "";
        Cursor cursor = db.rawQuery("SELECT " + Col2 + " FROM " + TABLE_NAME + " WHERE " + Col2 + " = " + nev, null);
        while(cursor.moveToNext()) {
            String val = cursor.getString(1);
            if(val.equals(nev)) {
                match = true;
                value = val;
                break;
            }
        }
        if(match) {
            db.execSQL("UPDATE " + TABLE_NAME + " SET " + Col3 + " = " + jegy + " WHERE " + Col2 + " = " + value);
            return true;
        } else {
            long result = db.insert(TABLE_NAME, null, contentValues);

            if(result == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }
}
