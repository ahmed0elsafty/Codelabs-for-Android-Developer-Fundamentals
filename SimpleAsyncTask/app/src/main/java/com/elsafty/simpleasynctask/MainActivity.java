package com.elsafty.simpleasynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String  TEXT_STATE = "currentText";
    private TextView mTextView;
    private Button mButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView);
        mButton = findViewById(R.id.button);
        mProgressBar= findViewById(R.id.progressBar);

        if (savedInstanceState !=null){
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }

    }

    public void startTask(View view) {
        mTextView.setText(null);
        mProgressBar.setProgress(0);
        new SimpleAsyncTask(mTextView,mProgressBar).execute();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE,mTextView.getText().toString().trim());
    }
}