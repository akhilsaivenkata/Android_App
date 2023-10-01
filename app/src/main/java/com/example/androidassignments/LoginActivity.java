package com.example.androidassignments;

import com.example.androidassignments.utils.utility;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        utility.print(this, "Debugging message: You are in LoginActivity.");

        //Button nextToListSai = findViewById(R.id.btnGoToListSai);
        //Button backToMainSai = findViewById(R.id.btnGoToMainSai);
        Button loginButtonSai = findViewById(R.id.LoginbtnSai);

        EditText emailEditTextSai = findViewById(R.id.editTextText);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String defaultEmail = "exampless@gmail.com"; // Default email if not found in SharedPreferences
        String savedEmail = sharedPreferences.getString("DefaultEmail", defaultEmail);
        emailEditTextSai.setText(savedEmail);

        /*nextToListSai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(LoginActivity.this, ListItemsActivity.class);
                startActivity(intent);
            }
        });

        backToMainSai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public void onLoginClickSai(View view) {
        // Get the email address from the email field
        EditText emailEditTextSaii = findViewById(R.id.editTextText);
        String emailSai = emailEditTextSaii.getText().toString();
        EditText passwordEditTextSai = findViewById(R.id.editTextTextPassword);
        String passwordSai = passwordEditTextSai.getText().toString();

        if (emailSai.isEmpty() || passwordSai.isEmpty() ){
            Toast.makeText(this, "Both email and password are required!!!.", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValidSai(emailSai)) {
            Toast.makeText(this, "email should be in valid format!!!.", Toast.LENGTH_SHORT).show();
        } else {
            // Save the email address to SharedPreferences
            SharedPreferences sharedPreferencesSai = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editorSai = sharedPreferencesSai.edit();
            editorSai.putString("DefaultEmail", emailSai);
            editorSai.apply();

            // Start MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    public boolean isEmailValidSai(String email) {
        // Define a regular expression for a basic email format.
        String emailPatternSai = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // Use the regular expression to validate the email.
        return email.matches(emailPatternSai);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Your code to start any necessary components or operations
        Log.i("LoginActivity","In onStart method of Login Activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Your code to resume any paused operations or refresh the UI
        Log.i("LoginActivity","In onResume method of Login Activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Your code to pause or save state if needed
        Log.i("LoginActivity","In onPause method of Login Activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Your code to stop any ongoing processes or cleanup
        Log.i("LoginActivity","In onStop method of Login Activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Your cleanup code before the activity is destroyed
        Log.i("LoginActivity","In onDestroyy method of Login Activity");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Your code to save instance state if needed
        Log.i("LoginActivity","In onSaveInstanceState method of Login Activity");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Your code to restore instance state if needed
        Log.i("LoginActivity","In onRestoreInstanceState method of Login Activity");
    }
}