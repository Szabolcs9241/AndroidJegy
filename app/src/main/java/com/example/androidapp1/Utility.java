package com.example.androidapp1;

import android.content.Context;
import android.widget.Toast;

public class Utility {

    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
