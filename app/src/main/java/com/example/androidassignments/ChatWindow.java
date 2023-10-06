package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private ListView listViewChat;
    private EditText editTextMsg;
    private Button sendBtn;
    private ArrayList<String> msgsSai;
    private ArrayAdapter<String> chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        // Here I am initializing UI components
        listViewChat = findViewById(R.id.listViewSai);
        editTextMsg = findViewById(R.id.editTextMsgSai);
        sendBtn = findViewById(R.id.btnSendSai);

        // Here I am initializing the chat messages and I am setting up an Adaptor
        msgsSai = new ArrayList<>();
        //chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, msgsSai);

        chatAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, msgsSai);

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

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return msgsSai.size();
        }

        @Override
        public String getItem(int position) {
            return msgsSai.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById(R.id.editTextMsgSai);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }
}