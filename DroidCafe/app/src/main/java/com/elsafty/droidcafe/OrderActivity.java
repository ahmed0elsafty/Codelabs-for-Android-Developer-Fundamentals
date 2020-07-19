package com.elsafty.droidcafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "OrderActivity";
    private TextView mRecieveMessage;
    private RadioButton mSameDay;
    private Spinner mSpinner;
    private String mSpinnerLabel;
    private EditText mPhone;
    private ArrayList<String> mSelectedBoxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mPhone = findViewById(R.id.phone_text);
        mSpinner = findViewById(R.id.spinner_label);
        mSameDay = findViewById(R.id.same_day);
        mRecieveMessage = findViewById(R.id.recieve_message);
        final Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.MESSAGE);
        mRecieveMessage.setText(message);
        mSameDay.setChecked(true);

        if (mSpinner != null) {
            mSpinner.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.labels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEND) {
                    dialNumber();
                    handled = true;
                }
                return handled;
            }
        });

    }

    private void dialNumber() {
        String phoneNum = mPhone.getText().toString().trim();
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNum));
        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialIntent);
        } else {
            Log.d(TAG, "dialNumber: nothing to dial");
        }
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

    public void showToast(View view) {
        String allSelected = "";
        if (mSelectedBoxes.size() == 0) return;
        for (String s : mSelectedBoxes) {
            allSelected += " " + s;
        }
        displayMessage(allSelected);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.chocolate_syrup:
                if (checked) {
                    mSelectedBoxes.add(getString(R.string.chocolate_syrup));
                } else {
                    mSelectedBoxes.remove(getString(R.string.chocolate_syrup));
                }
                break;
            case R.id.sprinkles:
                if (checked) {
                    mSelectedBoxes.add(getString(R.string.sprinkles));
                } else {
                    mSelectedBoxes.remove(getString(R.string.sprinkles));
                }
                break;
            case R.id.crushed_nuts:
                if (checked) {
                    mSelectedBoxes.add(getString(R.string.crushed_nuts));
                } else {
                    mSelectedBoxes.remove(getString(R.string.crushed_nuts));
                }
                break;
            case R.id.cherries:
                if (checked) {
                    mSelectedBoxes.add(getString(R.string.cherries));
                } else {
                    mSelectedBoxes.remove(getString(R.string.cherries));
                }
                break;
            case R.id.orio:
                if (checked) {
                    mSelectedBoxes.add(getString(R.string.orio_cookie_crumbles));
                } else {
                    mSelectedBoxes.remove(getString(R.string.orio_cookie_crumbles));
                }
                break;

        }
    }
}