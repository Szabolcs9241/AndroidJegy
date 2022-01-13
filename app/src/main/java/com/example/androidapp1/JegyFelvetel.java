package com.example.androidapp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class JegyFelvetel extends AppCompatActivity {

    JegyFelvetel instance;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this);
        instance = this;

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(Html.fromHtml("<font color='#f55742'>Hiba</font>"));
        builder1.setMessage("Nem adtál meg adatokat!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = builder1.create();

        final NumberPicker jegyPicker = findViewById(R.id.jegyPicker);
        jegyPicker.setMaxValue(5);
        jegyPicker.setMinValue(1);
        jegyPicker.setValue(1);
        jegyPicker.setWrapSelectorWheel(false);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            EditText nev = findViewById(R.id.editTextTextPersonName);

            if (nev.getText().toString().equals("") || !(jegyPicker.getValue() >= 1 && jegyPicker.getValue() <= 5)) {
                alertDialog.show();
            } else {
                addData(nev.getText().toString(), jegyPicker.getValue());
            }

        });

        Button ertekel = findViewById(R.id.button2);
        ertekel.setOnClickListener(v -> openJegyStatisztika());

    }

    public void openJegyStatisztika() {
        Intent intent = new Intent(this, JegyStatisztika.class);
        startActivity(intent);
    }

    public void addData(String nev, int jegy) {
        boolean insertData = database.addData(nev, jegy);
        if (insertData) {
            Utility.toastMessage(instance, "Sikeres rögzítés!");
        } else {
            Utility.toastMessage(instance, "Hiba a felvétel közben");
        }
    }
}