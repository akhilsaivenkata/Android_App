package com.example.androidassignments;

import com.example.androidassignments.utils.utility;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CAMERA_PERMISSION = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton imageButtonSai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        utility.print(this,"debug","ListItemsActivity" ,"Debugging message: You are in ListItemsActivity.");
        imageButtonSai = findViewById(R.id.imageButton);
        imageButtonSai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ListItemsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Request camera permission
                    ActivityCompat.requestPermissions(ListItemsActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_PERMISSION);
                } else {
                    // Permission is already granted; you can proceed with camera operations.
                    dispatchTakePictureIntent();
                }
            }
        });

        Switch switchControlSai = findViewById(R.id.switchSai);

        // Set an OnCheckedChangeListener to the Switch
        switchControlSai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Create a message based on the switch's state
                CharSequence text = isChecked ? getResources().getString(R.string.switchon) : getResources().getString(R.string.switchoff);
                int duration = isChecked ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;

                // Create and display the Toast message
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        });

        CheckBox checkBoxSai = findViewById(R.id.checkBoxSai); // Replace with the actual ID of your CheckBox

        checkBoxSai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show a dialog when the checkbox is checked
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                    builder.setMessage(R.string.dialogMessage)
                            .setTitle(R.string.dialogTitle)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button, finish the activity
                                    Intent resultIntent = new Intent(  );
                                    resultIntent.putExtra("Response", getResources().getString(R.string.response));
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    checkBoxSai.setChecked(false);
                                }
                            })
                            .show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with camera operations.
                imageButtonSai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispatchTakePictureIntent();
                    }
                });
            } else {
                PermissionDeniedMessageDisplay();
                // Permission denied, handle this case (e.g., show a message or disable camera functionality).
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageButtonSai.setImageBitmap(imageBitmap);
            }
        }
    }

    private void PermissionDeniedMessageDisplay() {
        // Show a message to the user indicating that the camera permission is required.
        Toast.makeText(this, "Camera permission is required to use the camera.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Your code to start any necessary components or operations
        utility.print(this,"debug","ListItemsActivity" ,"In onStart method of ListItemActivity");
        //Log.i("ListItemActivity","In onStart method of ListItemActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Your code to resume any paused operations or refresh the UI
        utility.print(this,"debug","ListItemsActivity" ,"In onResume method of ListItemActivity");
        //Log.i("ListItemActivity","In onResume method of ListItemActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Your code to pause or save state if needed
        utility.print(this,"debug","ListItemsActivity" ,"In onPause method of ListItemActivity");
        //Log.i("ListItemActivity","In onPause method of ListItemActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Your code to stop any ongoing processes or cleanup
        utility.print(this,"debug","ListItemsActivity" ,"In onStop method of ListItemActivity");
        //Log.i("ListItemActivity","In onStop method of ListItemActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Your cleanup code before the activity is destroyed
        utility.print(this,"debug","ListItemsActivity" ,"In onDestroy method of ListItemActivity");
        //Log.i("ListItemActivity","In onDestroy method of ListItemActivity");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Your code to save instance state if needed
        utility.print(this,"debug","ListItemsActivity" ,"In onSaveInstanceState method of ListItemActivity");
        //Log.i("ListItemActivity","In onSaveInstanceState method of ListItemActivity");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Your code to restore instance state if needed
        utility.print(this,"debug","ListItemsActivity" ,"In onRestoreInstanceState method of ListItemActivity");
        //Log.i("ListItemActivity","In onRestoreInstanceState method of ListItemActivity");
    }
}