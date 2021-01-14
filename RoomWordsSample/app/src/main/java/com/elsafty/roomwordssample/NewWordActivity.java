package com.elsafty.roomwordssample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.elsafty.roomwordssample.db.Word;

public class NewWordActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.elsafty.roomwordssample.REPLY";
    private EditText mEditText;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditText = findViewById(R.id.edit_word);
        mSaveButton = findViewById(R.id.button_save);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replayIntent = new Intent();
                if (TextUtils.isEmpty(mEditText.getText().toString())) {
                    setResult(RESULT_CANCELED, replayIntent);
                } else {
                    replayIntent.putExtra(EXTRA_REPLY, mEditText.getText().toString());
                    setResult(RESULT_OK, replayIntent);
                    finish();
                }
            }
        });
    }
}