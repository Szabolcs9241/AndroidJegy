package com.example.androidapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class JegyStatisztika extends AppCompatActivity {

    Database database;
    JegyStatisztika instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        database = new Database(this);
        loadData();
        instance = this;

        Button button = findViewById(R.id.button3);
        button.setOnClickListener(v -> {
            database.dropTable();
            Utility.toastMessage(instance, "Az adatok sikeresen törlésre kerültek!");
            openJegyFelvetel();
            finish();
        });

    }

    @SuppressLint("DefaultLocale")
    private void loadData() {
        Cursor data = database.getData();
        ArrayList<String> list = new ArrayList<>();
        while (data.moveToNext()) {
            list.add(data.getString(1) + " - " + String.format("%.2f", ((double) data.getInt(2) / data.getInt(3))));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListView lw = findViewById(R.id.listView);
        lw.setAdapter(adapter);
    }

    public void openJegyFelvetel() {
        Intent intent = new Intent(this, JegyFelvetel.class);
        startActivity(intent);
    }
}