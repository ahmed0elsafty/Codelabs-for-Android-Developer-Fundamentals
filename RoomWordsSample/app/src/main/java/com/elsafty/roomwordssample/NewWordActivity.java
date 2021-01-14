package com.elsafty.roomwordssample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.elsafty.roomwordssample.REPLY";
    public static final String EXTRA_UPDATE =
            "com.elsafty.roomwordssample.UPDATE";
    private EditText mEditText;
    private Button mSaveButton;
    private int mId = -1;
    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditText = findViewById(R.id.edit_word);
        mSaveButton = findViewById(R.id.button_save);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(MainActivity.UPATED_WORD_TEXT)) {
                word = intent.getStringExtra(MainActivity.UPATED_WORD_TEXT);
                mId = intent.getIntExtra(MainActivity.UPATED_WORD_ID,-1);
                mEditText.setText(word);
            }
        }
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replayIntent = new Intent();
                if (TextUtils.isEmpty(mEditText.getText().toString())) {
                    setResult(RESULT_CANCELED, replayIntent);
                } else {
                    replayIntent.putExtra(EXTRA_REPLY, mEditText.getText().toString());
                    if (mId != -1) {
                        replayIntent.putExtra(EXTRA_UPDATE,mId);
                    }
                    setResult(RESULT_OK, replayIntent);
                    finish();
                }
            }
        });
    }
}