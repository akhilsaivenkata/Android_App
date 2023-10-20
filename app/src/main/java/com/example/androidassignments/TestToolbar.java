package com.example.androidassignments;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidassignments.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu from the XML resource file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Use a switch statement to determine which item was selected

        if (id == R.id.action_oneSai) {
            Log.d("Toolbar", "Choice 1 selected");
            Snackbar.make(findViewById(R.id.action_oneSai), "You selected item 1", Snackbar.LENGTH_SHORT).show();
            // Handle Choice 1
            return true;
        } else if (id == R.id.action_twoSai) {
            // Handle Choice 2
            Log.d("Toolbar", "Choice 2 selected");
            Snackbar.make(findViewById(R.id.action_twoSai), "You selected item 2", Snackbar.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.ToolDialog);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button, finish the current activity
                    finish();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog, do nothing
                }
            });

            // Create the AlertDialog and show it
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        } else if (id == R.id.action_threeSai) {
            // Handle Choice 3
            Log.d("Toolbar", "Choice 3 selected");
            Snackbar.make(findViewById(R.id.action_threeSai), "You selected item 3", Snackbar.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.about_itemSai) {
            // toast msg details
            String versionInfo = "Version 1.0, by Sai Venkata Akhil";
            Toast.makeText(this, versionInfo, Toast.LENGTH_SHORT).show();
            Log.d("Toolbar", "About item selected");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}