package com.example.androidapp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    DatabaseHelper databaseHelper;
    //private Button btnAdd, btnViewData;
    //private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(Html.fromHtml("<font color='#f55742'>Hiba</font>"));
        builder1.setMessage("Nem adtál meg adatokat!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        /*
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
         */
        AlertDialog alertDialog = builder1.create();

        final NumberPicker jegyPicker = (NumberPicker) findViewById(R.id.jegyPicker);
        jegyPicker.setMaxValue(5);
        jegyPicker.setMinValue(1);
        jegyPicker.setValue(1);
        jegyPicker.setWrapSelectorWheel(false);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                /*
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("HELLLOOOOOO");
                 */

                EditText nev = (EditText) findViewById(R.id.editTextTextPersonName);

                if(nev.getText().toString().equals("") || !(jegyPicker.getValue() >= 1 && jegyPicker.getValue() <= 5)) {
                    alertDialog.show();
                } else {
                    addData(nev.getText().toString(), String.valueOf(jegyPicker.getValue()));
                }

            }
        });

        Button ertekel = (Button) findViewById(R.id.button2);
        ertekel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openMainActivity2();

                /*
                Cursor data = databaseHelper.getData();
                ArrayList<String> list = new ArrayList<String>();
                while (data.moveToNext()) {
                    list.add(data.getString(1));
                }
                toastMessage(list.get(0));
                 */
            }
        });

    }

    public void openMainActivity2() {
        Intent intent = new Intent (this, MainActivity2.class);
        startActivity(intent);
    }

    public void addData(String nev, String jegy) {
        boolean insertData = databaseHelper.addData(nev, jegy);
        if(insertData) {
            toastMessage("Inserted");
        } else {
            toastMessage("Unexpected error");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}