package com.example.androidassignments;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.androidassignments.utils.utility;
import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        toolbarSai= findViewById(R.id.toolBarSai);
        setSupportActionBar(toolbarSai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.chatAct));
        /*ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            Log.i("ChatActivity",getResources().getString(R.string.chatAct));
            actionBar.setTitle(getResources().getString(R.string.chatAct));
            actionBar.setCustomView(R.id.toolBarSai);
        }*/

        // Here I am initializing UI components
        listViewChat = findViewById(R.id.listViewSai);
        editTextMsg = findViewById(R.id.editTextMsgSai);
        sendBtn = findViewById(R.id.btnSendSai);

        // Here I am initializing the chat messages and I am setting up an Adaptor
        msgsSai = new ArrayList<>();
        //chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, msgsSai);

        /////chatAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, msgsSai);
        chatAdapter = new ChatAdapter(this,0,msgsSai);
        listViewChat.setAdapter(chatAdapter);


        // Here I am setting up an onClick listener for the Sendbtn
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the EditText field
                String message = editTextMsg.getText().toString().trim();

                if (!message.isEmpty()) {
                    // here it's adding msg to ArrayList
                    msgsSai.add("You: " + message);

                    // Telling adapter that the data has changed
                    chatAdapter.notifyDataSetChanged();

                    // Clear the EditText field
                    editTextMsg.getText().clear();
                }
            }
        });
    }

    public void onPrevButtonClick(View view) {
        // Handle the click event here
        NavUtils.navigateUpFromSameTask(this);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

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
}