package com.elsafty.droidcafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private TextView mRecieveMessage;
    private RadioButton mSameDay;
    private Spinner mSpinner;
    private String mSpinnerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mSpinner = findViewById(R.id.spinner_label);
        mSameDay = findViewById(R.id.same_day);
        mRecieveMessage = findViewById(R.id.recieve_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.MESSAGE);
        mRecieveMessage.setText(message);
        mSameDay.setChecked(true);

        if (mSpinner != null) {
            mSpinner.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.labels_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.same_day:
                if (checked) {
                    displayMessage(getString(R.string.same_day_messenger_service));
                }
                break;
            case R.id.next_day:
                if (checked) {
                    displayMessage(getString(R.string.next_day_ground_delivery));
                }
                break;
            case R.id.pick_up:
                if (checked) {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        mSpinnerLabel = (String) adapterView.getItemAtPosition(position);
        displayMessage(mSpinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}