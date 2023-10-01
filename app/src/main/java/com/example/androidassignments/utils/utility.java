package com.example.androidassignments.utils;

import android.content.Context;
import android.widget.Toast;

public class utility {
    public static void print(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
