package com.example.androidassignments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {
    private static final String ARG_MESSAGE = "message";
    private static final String ARG_MESSAGE_ID = "messageId";

    private String msgsSai;
    private long msgsid;

    boolean istablet;

    public MessageFragment() {
        // Required empty public constructor
    }
    public static MessageFragment newInstance(String message, long messageId) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        args.putLong(ARG_MESSAGE_ID, messageId);
        //args.putBoolean("isTablet", isTablet );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_details, container, false);

        // Retrieve arguments passed to the fragment
        Bundle args = getArguments();
        if ( args!= null) {

            String message = args.getString(ARG_MESSAGE, "");
            long messageId = args.getLong(ARG_MESSAGE_ID, -1);
            Log.i("yiyi",message);
            istablet = args.getBoolean("isTablet", false);



            TextView messageTextView = view.findViewById(R.id.textMessage);
            TextView idTextView = view.findViewById(R.id.textId);

            messageTextView.setText(getResources().getString(R.string.chatmsg) +message);
            idTextView.setText(getResources().getString(R.string.chatmsgid) + messageId);




            Button deleteButton = view.findViewById(R.id.btnDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // I'm passing the message ID back to the calling activity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("deletedMessageId", messageId);


                    getActivity().setResult(Activity.RESULT_OK, resultIntent);

                    // Finish the activity
                    //getActivity().finish();
                    if (!istablet) {
                        getActivity().finish();
                    }
                }
            });
        }

        return view;
    }
    private boolean isTablet() {

        return getResources().getBoolean(R.bool.is_tablet);
    }
}
