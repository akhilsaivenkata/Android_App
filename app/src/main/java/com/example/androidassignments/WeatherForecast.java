package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherForecast extends AppCompatActivity {
    private Toolbar toolbarSai;

    private ImageView imageViewWeather;
    private TextView textViewCurrentTemperature;
    private TextView textViewMinTemperature;
    private TextView textViewMaxTemperature;
    private ProgressBar progressBar;

    private static final String API_KEY = "92c953df3fe2a545fd2f40bc6688bcc5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        toolbarSai= findViewById(R.id.toolBarSai);
        setSupportActionBar(toolbarSai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.WeatherAct));

        // Here I am finding ProgressBar in the layout using its ID
        progressBar = findViewById(R.id.progressBar);

        // Set the visibility of the ProgressBar to View.VISIBLE
        progressBar.setVisibility(View.VISIBLE);
        
        imageViewWeather = findViewById(R.id.imageViewWeather);
        textViewCurrentTemperature = findViewById(R.id.textViewCurrentTemperature);
        textViewMinTemperature = findViewById(R.id.textViewMinTemperature);
        textViewMaxTemperature = findViewById(R.id.textViewMaxTemperature);

       

        ForecastQuery forecastQuery = new ForecastQuery();
        forecastQuery.execute();
    }

    // Inner class for AsyncTask
    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        // Variables for min, max, and current temperature
        private String minTemperature;
        private String maxTemperature;
        private String currentTemperature;
        private Bitmap weatherBitmap;
        private String iconName;
        private String iconUrl;



        protected String doInBackground(String... args) {
            try {
                String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=92c953df3fe2a545fd2f40bc6688bcc5&mode=xml&units=metric";

                // Make HTTP request
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                // Get the InputStream from the connection
                InputStream stream = connection.getInputStream();

                // Parse the XML response
                XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser parser = xmlFactoryObject.newPullParser();
                parser.setInput(stream, null);

                parseXml(parser); // Call a separate method to handle XML parsing

                // Close the InputStream
                stream.close();

                // Check if the image is already present locally
                if (!fileExistance(iconName + ".png")) {
                    // Download the image and save it to local storage
                    weatherBitmap = downloadImage(iconUrl);
                    saveImageLocally(iconName, weatherBitmap);
                } else {
                    // Read the image from local storage
                    weatherBitmap = readImageLocally(iconName);
                    Log.i("ForecastQuery", "Image found locally: " + iconName);
                }

                // Show progress for the weather icon attribute
                publishProgress(100);

                // Return success message
                return "Success";
            } catch (Exception e) {
                Log.e("ForecastQuery", "Error in doInBackground: " + e.getMessage());
                return "Error";
            }
        }



        private void parseXml(XmlPullParser parser) {
            try {
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = parser.getName();
                    if (eventType == XmlPullParser.START_TAG) {
                        if ("temperature".equals(tagName)) {
                            currentTemperature = parser.getAttributeValue(null, "value");
                            minTemperature = parser.getAttributeValue(null, "min");
                            maxTemperature = parser.getAttributeValue(null, "max");

                            // Show progress for temperature attributes
                            publishProgress(25, 50, 75);
                        } else if ("weather".equals(tagName)) {
                            // Extract the "icon" attribute
                            iconName = parser.getAttributeValue(null, "icon");
                            // Build the URL for weather icon
                            iconUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
                            // Download and set the weather icon
                            weatherBitmap = downloadImage(iconUrl);

                            // Show progress for the weather icon attribute
                            publishProgress(100);
                        }
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                Log.e("ForecastQuery", "Error parsing XML: " + e.getMessage());
            }
        }

        private boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        private Bitmap downloadImage(String imageUrl) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e("ForecastQuery", "Error downloading image: " + e.getMessage());
                return null;
            }
        }

        private void saveImageLocally(String iconName, Bitmap image) {
            try {
                FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
                Log.i("ForecastQuery", "Image saved locally: " + iconName);
            } catch (Exception e) {
                Log.e("ForecastQuery", "Error saving image locally: " + e.getMessage());
            }
        }

        private Bitmap readImageLocally(String iconName) {
            try {
                FileInputStream fis = openFileInput(iconName + ".png");
                return BitmapFactory.decodeStream(fis);
            } catch (FileNotFoundException e) {
                Log.e("ForecastQuery", "Error reading image locally: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        protected void onPostExecute(String result) {
            // Update UI components with parsed data
            progressBar.setVisibility(View.INVISIBLE);
            textViewCurrentTemperature.setText("Current Temp: " + currentTemperature);
            textViewMinTemperature.setText("Min Temp: " + minTemperature);
            textViewMaxTemperature.setText("Max Temp: " + maxTemperature);
            imageViewWeather.setImageBitmap(weatherBitmap);
        }

        private void updateUI() {
            // Update UI elements with the fetched weather data
            // For example, set TextView texts, ImageView images, etc.
            // Hide the ProgressBar when the data is ready
            //ProgressBar progressBar = findViewById(R.id.progressBar);
            //progressBar.setVisibility(View.INVISIBLE);
            TextView minTempTextView = findViewById(R.id.textViewMinTemperature);
            TextView maxTempTextView = findViewById(R.id.textViewMaxTemperature);
            TextView currentTempTextView = findViewById(R.id.textViewCurrentTemperature);

            if (minTemperature != null) {
                minTempTextView.setText("Min Temperature: " + minTemperature + "°C");
            }

            if (maxTemperature != null) {
                maxTempTextView.setText("Max Temperature: " + maxTemperature + "°C");
            }

            if (currentTemperature != null) {
                currentTempTextView.setText("Current Temperature: " + currentTemperature + "°C");
            }

            // Update ImageView with the downloaded Bitmap
            ImageView weatherImageView = findViewById(R.id.imageViewWeather);
            if (weatherBitmap != null) {
                weatherImageView.setImageBitmap(weatherBitmap);
            }
        }
    }

}