package com.elsafty.powerreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    CustomReceiver mCustomReceiver;
    IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mCustomReceiver = new CustomReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        mIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        mIntentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        this.registerReceiver(mCustomReceiver, mIntentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(mCustomReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mCustomReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mCustomReceiver);
    }

    public void sendCustomBroadcast(View view) {
        Random random = new Random();
        int ranNumber = random.nextInt(20);
        String messageExtra = "Square of the Random Number : " + (ranNumber * ranNumber);
        Intent sendBroadcast = new Intent(ACTION_CUSTOM_BROADCAST);
        sendBroadcast.putExtra(ACTION_CUSTOM_BROADCAST,messageExtra);
        LocalBroadcastManager.getInstance(this).sendBroadcast(sendBroadcast);
    }
}