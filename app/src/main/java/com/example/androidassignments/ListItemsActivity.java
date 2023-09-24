package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        Button backToLoginSai = findViewById(R.id.button6);
        Button homeToMainSai = findViewById(R.id.button7);
        backToLoginSai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(ListItemsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        homeToMainSai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(ListItemsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}