package com.example.androidapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        databaseHelper = new DatabaseHelper(this);

        loadData();


        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                databaseHelper.dropTable();
                toastMessage("Az adatok sikeresen törlésre kerültek!");
            }
        });

    }

    private void loadData() {
        Cursor data = databaseHelper.getData();
        ArrayList<String> list = new ArrayList<String>();
        while (data.moveToNext()) {
            list.add(data.getString(1) + " - " + data.getString(2));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListView lw = (ListView) findViewById(R.id.listView);
        lw.setAdapter(adapter);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}