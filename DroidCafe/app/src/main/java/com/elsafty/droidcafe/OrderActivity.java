package com.elsafty.droidcafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {
    private TextView mRecieveMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mRecieveMessage = findViewById(R.id.recieve_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.MESSAGE);
        mRecieveMessage.setText(message);
    }
}