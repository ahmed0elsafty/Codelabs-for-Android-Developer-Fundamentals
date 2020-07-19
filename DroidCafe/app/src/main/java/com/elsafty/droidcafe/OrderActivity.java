package com.elsafty.droidcafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.same_day:
                if (checked){
                    displayMessage(getString(R.string.same_day_messenger_service));
                }
                break;
            case R.id.next_day:
                if (checked){
                    displayMessage(getString(R.string.next_day_ground_delivery));
                }
                break;
            case R.id.pick_up:
                if (checked){
                    displayMessage(getString(R.string.pick_up));
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void displayMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}