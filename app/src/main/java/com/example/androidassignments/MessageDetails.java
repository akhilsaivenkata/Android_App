package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MessageDetails extends AppCompatActivity {
    private String msgsSai;
    private long msgsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        String msgsSai = getIntent().getStringExtra("message");
        long msgsid = getIntent().getLongExtra("messageId", -1);

        // Here I am creating a bundle to pass information to the fragment
        Bundle bundle = new Bundle();
        bundle.putString("message", msgsSai);
        bundle.putLong("messageId", msgsid);

        // Create a fragment instance and set the arguments
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(bundle);

        // Add the fragment to the empty FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,  MessageFragment.newInstance(msgsSai, msgsid))
                    .commit();
        }
        setResult(RESULT_OK);
    }
}