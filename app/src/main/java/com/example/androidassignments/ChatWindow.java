package com.example.androidassignments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.androidassignments.utils.utility;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.example.androidassignments.utils.utility;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity implements  AdapterView.OnItemClickListener{

    ListView listViewChat;
    EditText editTextMsg;
    Button sendBtn;
    ArrayList<String> msgsSai;
    ArrayAdapter<String> chatAdapter;
    Toolbar toolbarSai;
    private ChatDatabaseHelper databaseHelper; // Add a field for the database helper
    private SQLiteDatabase database;
    private static final String KEY_MESSAGE = "message";
    private boolean isTabletLayout=false;
    private Cursor cursor;
    private ArrayList<Long> msgsid;
    private static final int REQUEST_CODE_DETAILS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        toolbarSai = findViewById(R.id.toolBarSai);
        setSupportActionBar(toolbarSai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.chatAct));
        FrameLayout frameLayout = findViewById(R.id.FrameLayoutSai);
        if (frameLayout != null) {
            // Tablet layout (FrameLayout is present)
            isTabletLayout = true;

        } else {
            // Phone layout (FrameLayout is not present)
            isTabletLayout = false;

        }
        // Here I am initializing UI components
        listViewChat = findViewById(R.id.listViewSai);
        editTextMsg = findViewById(R.id.editTextMsgSai);
        sendBtn = findViewById(R.id.btnSendSai);
        msgsSai = new ArrayList<>();
        msgsid = new ArrayList<>();

        databaseHelper = new ChatDatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        chatAdapter = new ChatAdapter(this, 0, msgsSai, msgsid);
        listViewChat.setAdapter(chatAdapter);

        listViewChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("yippi",String.valueOf(id));
                showMsgsDetails(msgsSai.get(position), id);
            }
        });

        //loadChatMessagesFromDatabase();

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
                    chatAdapter.notifyDataSetChanged();

                    editTextMsg.getText().clear();
                }
            }
        });

        cursor = database.query(ChatDatabaseHelper.TABLE_NAME,
                new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                null, null, null, null, null);

        if (cursor != null) {

            // Printing the column name
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.i("ChatWindowActivity", "columnName: " + cursor.getColumnName(i));
            }
            while (cursor.moveToNext()) {
                long messageId = cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                Log.i("ChatWindowActivity", "Db msgs :" + message);
                msgsSai.add(message);
                msgsid.add(messageId);
                Log.i("tagtag",String.valueOf(messageId));
            }
            while (!cursor.isAfterLast()) {
                Log.i("ChatWindowActivity", "sql msgs:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                Log.i("ChatWindowActivity", "column count =" + cursor.getColumnCount());
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Retrieve the selected message and its ID
        String msgSelected = msgsSai.get(position);
        long msgIdSelected = msgsid.get(position);
        Log.i("gatgat",String.valueOf(msgIdSelected));

        if (isTabletLayout && getResources().getConfiguration().screenWidthDp >= 600) {
            // Tablet layout with a screen width of at least 600dp
            showDetailsFragment(msgSelected, msgIdSelected);
        } else {
            // Phone layout
            startDetailsActivity(msgSelected, msgIdSelected);
        }
    }

    private void showDetailsFragment(String message, long msgsid) {

        // Create a new instance of your MessageDetailsFragment
        MessageFragment detailsFragment = MessageFragment.newInstance(message, msgsid);

        // Start a FragmentTransaction to add the fragment to the FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FrameLayoutSai, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void startDetailsActivity(String msg, long msgsId) {

        Intent intent = new Intent(this, MessageDetails.class);
        intent.putExtra("message", msg);
        intent.putExtra("messageId", msgsId);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected class ChatAdapter extends ArrayAdapter<String> {
        private ArrayList<Long> msgsid;
        //private Cursor cursor; // Added cursor as a class variable

        public ChatAdapter(Context ctx, int resource, ArrayList<String> data, ArrayList<Long> msgsid) {
            super(ctx, 0, data);
            this.msgsid = msgsid;
        }

        public int getCount() {

            return msgsSai.size();
        }

        @Override
        public String getItem(int position) {

            return msgsSai.get(position);
        }


        public long getItemId(int position) {

            if(cursor==null){
                Log.i("NUL","nulll");
            }
            if (cursor != null && cursor.moveToPosition(position)) {
                Log.i("goutham",String.valueOf(position));
                return cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
            } else {
                return -1;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                utility.print(ChatWindow.this, "debug", "ChatWindowActivity", "Inside incoming chat");
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                utility.print(ChatWindow.this, "debug", "ChatWindowActivity", "Inside outgoing chat");
            }

            //TextView message = (TextView) result.findViewById(R.id.editTextMsgSai);
            TextView message = (TextView) result.findViewById(R.id.message_text1);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }

    private void showMsgsDetails(String message, long msgId) {


        if (ismobile()) {
            // If running on a phone, start a new activity
            Intent intent = new Intent(this, MessageDetails.class);
            intent.putExtra("message", message);
            intent.putExtra("messageId", msgId);
            // startActivity(intent);
            Log.i("yoyo",message);
            Log.i("yoyo",String.valueOf(msgId));
            startActivityForResult(intent, REQUEST_CODE_DETAILS);
        } else {
            // If running on a tablet, show the details fragment
            MessageFragment fragment = MessageFragment.newInstance(message, msgId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private boolean ismobile() {

        // Check if the device is a phone (based on screen size, for example)
        return getResources().getBoolean(R.bool.is_mobile);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DETAILS && resultCode == RESULT_OK) {
            // Here I am handlng the result from the MessageDetailsActivity if at all its needed
            if (data != null && data.hasExtra("deletedMessageId")) {
                long deletedMessageId = data.getLongExtra("deletedMessageId", -1);

                // Delete the message with the specified ID
                deleteMessage(deletedMessageId);

                // Update the ListView
                updateListView();
                finish();
            }
        }
    }

    private void deleteMessage(long messageId) {

        // this method is for deleting the selected id from the msgs
        database.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + " = ?", new String[]{String.valueOf(messageId)});
        msgsSai.remove(messageId);

    }

    private void updateListView() {


        if (chatAdapter != null) {
            chatAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        // I'm closing the database if it's open
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

}