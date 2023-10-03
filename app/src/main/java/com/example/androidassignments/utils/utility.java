package com.example.androidassignments.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class utility {
    public static void print(Context context, String type, String tag,String message) {
        if(type=="toast"){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
        if(type=="debug"){
            Log.d(tag,message);
        }

    }
}
