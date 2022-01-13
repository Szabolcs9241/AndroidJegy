package com.example.androidapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
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

        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                database.dropTable();
                Utility.toastMessage(instance,"Az adatok sikeresen törlésre kerültek!");
                openMainActivity();
                finish();
            }
        });

    }

    private void loadData() {
        Cursor data = database.getData();
        ArrayList<String> list = new ArrayList<String>();
        while (data.moveToNext()) {
            list.add(data.getString(1) + " - " + String.format("%.2f", (Double.valueOf(data.getInt(2)) / data.getInt(3))));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListView lw = (ListView) findViewById(R.id.listView);
        lw.setAdapter(adapter);
    }

    public void openMainActivity() {
        Intent intent = new Intent (this, JegyFelvetel.class);
        startActivity(intent);
    }
}