package com.example.androidassignments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.androidassignments.utils.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.example.androidassignments.utils.utility;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    ListView listViewChat;
    EditText editTextMsg;
    Button sendBtn;
    ArrayList<String> msgsSai;
    ArrayAdapter<String> chatAdapter;
    Toolbar toolbarSai;
    private ChatDatabaseHelper databaseHelper; // Add a field for the database helper
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);


        toolbarSai= findViewById(R.id.toolBarSai);
        setSupportActionBar(toolbarSai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.chatAct));

        // Here I am initializing UI components
        listViewChat = findViewById(R.id.listViewSai);
        editTextMsg = findViewById(R.id.editTextMsgSai);
        sendBtn = findViewById(R.id.btnSendSai);
        msgsSai = new ArrayList<>();

        chatAdapter = new ChatAdapter(this, 0, msgsSai);
        listViewChat.setAdapter(chatAdapter);

        //I am creating an instance of chatdatabasehelper and writable database
        databaseHelper = new ChatDatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        // I am retrieving existing chat messages from the database
        loadChatMessagesFromDatabase();


        chatAdapter = new ChatAdapter(this,0,msgsSai);
        listViewChat.setAdapter(chatAdapter);


        // Here I am setting up an onClick listener for the Sendbtn
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the EditText field
                String message = editTextMsg.getText().toString().trim();


                if (!message.isEmpty()) {
                    ContentValues values = new ContentValues();
                    values.put(ChatDatabaseHelper.KEY_MESSAGE, message);
                    long newRowId = database.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
                    if (newRowId != -1) {
                        Log.i("ChatWindowActivity", "Inserted new message into the database with ID: " + newRowId);
                    } else {
                        Log.e("ChatWindowActivity", "Failed to insert new message into the database");
                    }
                    // here it's adding msg to ArrayList
                    msgsSai.add("You: " + message);

                    // Telling adapter that the data has changed
                    chatAdapter.notifyDataSetChanged();

                    // I'm clearing the EditText field
                    editTextMsg.getText().clear();
                }
            }
        });
    }

    private void loadChatMessagesFromDatabase() {
        //ArrayList<String> msgsSai = new ArrayList<>();
        msgsSai.clear();
        // Define the table name and columns
        String tableName = ChatDatabaseHelper.TABLE_NAME;
        String[] columns = {ChatDatabaseHelper.KEY_MESSAGE};

        // Query the database to retrieve chat messages
        Cursor cursor = database.query(tableName, columns, null, null, null, null, null);

        // Log the column count and column names
        Log.i("ChatWindowActivity", "Cursor's column count = " + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i("ChatWindowActivity", "Column Name: " + cursor.getColumnName(i));
        }

        // I am iterating through the cursor to retrieve chat msgs

        if (cursor.moveToFirst()) {
            do {
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                Log.i("ChatWindowActivity", "SQL MESSAGE: " + message);
                msgsSai.add("You: " + message);
            } while (cursor.moveToNext());
        }

        // I am updating the chatAdapter with retrieved messages
        chatAdapter.notifyDataSetChanged();

        // Close the cursor
        cursor.close();
    }

    public void onPrevButtonClick(View view) {
        // I'm  handling the click event here
        NavUtils.navigateUpFromSameTask(this);
    }


    protected class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx,int resource, ArrayList<String> data) {

            /////super(ctx, 0);
            super(ctx,0,data);
        }

        @Override
        public int getCount() {
            /////return msgsSai.size();
            return super.getCount();
        }

        @Override
        public String getItem(int position) {
            /////return msgsSai.get(position);
            return super.getItem(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                utility.print(ChatWindow.this,"debug","ChatWindowActivity","Inside incoming chat");
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                utility.print(ChatWindow.this,"debug","ChatWindowActivity","Inside outgoing chat");
            }

            //TextView message = (TextView) result.findViewById(R.id.editTextMsgSai);
            TextView message = (TextView) result.findViewById(R.id.message_text1);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // I'm closing the database if it's open
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}