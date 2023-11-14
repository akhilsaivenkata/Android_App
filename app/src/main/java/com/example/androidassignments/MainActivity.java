package com.example.androidassignments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.androidassignments.utils.utility;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 10;
    private Toolbar toolbarSai;
    private Spinner citySpinner;
    private String selectedCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarSai= findViewById(R.id.toolBarSai);
        setSupportActionBar(toolbarSai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.MainAct));

        Button nextToListItemsSai = findViewById(R.id.button);
        utility.print(this,"debug","MainActivity","Debugging message: You are in MainActivity.");

        //utility.print(this,"Debugging message: You are in MainActivity.");

        nextToListItemsSai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        citySpinner = findViewById(R.id.citySpinner);

        // I am setting up the ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.cityArray,
                android.R.layout.simple_spinner_item
        );


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        citySpinner.setAdapter(adapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected city
                selectedCity = parentView.getItemAtPosition(position).toString().toLowerCase();
                // You can do something with the selected city, such as storing it or displaying it.
                // For now, let's just print it to the log.
                Log.d("MainActivity", "Selected City: " + selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });

    }

    public void onPrevButtonClick(View view) {
        // Handle the click event here
        NavUtils.navigateUpFromSameTask(this);
    }

    private String getWeatherApiUrl(String city) {
        String apiKey = "92c953df3fe2a545fd2f40bc6688bcc5";  // Replace with your actual API key
        return "http://api.openweathermap.org/data/2.5/weather?q=" + city + ",ca&APPID=" + apiKey + "&mode=xml&units=metric";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            // Handle the result from ListItemsActivity
            if (resultCode == RESULT_OK) {
                // The activity returned successfully
                //Log.i("MainActivity", "Returned to MainActivity onActivityResult");
                utility.print(this,"debug","MainActivity","Returned to MainActivity onActivityResult");

                String messagePassedSai = data.getStringExtra("Response");

                if (messagePassedSai != null) {
                    // Display a Toast with the retrieved message
                    String toastMessage = getResources().getString(R.string.LIAP) + messagePassedSai;
                    int duration = Toast.LENGTH_SHORT; // You can use LENGTH_LONG if needed

                    Toast toast = Toast.makeText(getApplicationContext(), toastMessage, duration);
                    toast.show();
                }
            } else {
                // The activity may have been canceled or encountered an error
                utility.print(this,"debug","MainActivity","ListItemsActivity did not return successfully");
                //Log.i("MainActivity", "ListItemsActivity did not return successfully");
            }
        }
    }

    public void chatButtonStartOnClick(View v){
        utility.print(this,"debug","MainActivity","User clicked Start Chat");
        Intent intent = new Intent(MainActivity.this, ChatWindow.class);
        startActivity(intent);
    }

    public void toolbarButtonStartOnClick(View v){
        utility.print(this,"debug","MainActivity","User clicked test tool bar");
        Intent intent = new Intent(MainActivity.this, TestToolbar.class);
        startActivity(intent);
    }
    public void forecastButtonStartOnClick(View v){
        if (selectedCity != null && !selectedCity.isEmpty()) {
            // Construct the API URL based on the selected city
            String apiUrl = getWeatherApiUrl(selectedCity);

            // Navigating to the WeatherForecast activity and pass the API URL
            Intent intent = new Intent(MainActivity.this, WeatherForecast.class);
            intent.putExtra("weatherApiUrl", apiUrl);
            startActivity(intent);
        } else {
            // Handle the case where no city is selected
            Toast.makeText(MainActivity.this, "Please select a city first", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Your code to start any necessary components or operations
        utility.print(this,"debug","MainActivity","In onStart method of Main Activity");
        //Log.i("MainActivity","In onStart method of Main Activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Your code to resume any paused operations or refresh the UI
        utility.print(this,"debug","MainActivity","In onResume method of Main Activity");
        //Log.i("MainActivity","In onResume method of Main Activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Your code to pause or save state if needed
        utility.print(this,"debug","MainActivity","In onPause method of Main Activity");
        //Log.i("MainActivity","In onPause method of Main Activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Your code to stop any ongoing processes or cleanup
        utility.print(this,"debug","MainActivity","In onStop method of Main Activity");
        //Log.i("MainActivity","In onStop method of Main Activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Your cleanup code before the activity is destroyed
        utility.print(this,"debug","MainActivity","In onDestriy method of Main Activity");
        //Log.i("MainActivity","In onDestriy method of Main Activity");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Your code to save instance state if needed
        utility.print(this,"debug","MainActivity","In onSaveInstanceState method of Main Activity");
        //Log.i("MainActivity","In onSaveInstanceState method of Main Activity");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Your code to restore instance state if needed
        utility.print(this,"debug","MainActivity","In onRestoreInstanceState method of Main Activity");
        //Log.i("MainActivity","In onRestoreInstanceState method of Main Activity");
    }
}