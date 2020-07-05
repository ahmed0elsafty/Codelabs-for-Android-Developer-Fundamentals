package com.elsafty.simpleasynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String  TEXT_STATE = "currentText";
    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView);
        mButton = findViewById(R.id.button);

        if (savedInstanceState !=null){
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    public void startTask(View view) {
        mTextView.setText(null);
        new SimpleAsyncTask(mTextView).execute();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(TEXT_STATE,mTextView.getText().toString().trim());
    }

    class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {
        WeakReference<TextView> mTextView;

        public SimpleAsyncTask(TextView textView) {
            this.mTextView = new WeakReference<>(textView);
        }

        @Override
        protected String doInBackground(Void... voids) {
            Random random = new Random();
            int n = random.nextInt(11);
            int s = n * 200;
            publishProgress(s);
            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Awake at last after sleeping for " + s + " milliseconds!";
        }

        @Override
        protected void onPostExecute(String s) {
            mTextView.get().setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mTextView.get().setText("Waiting Me "+ values[0]+" ms");
        }
    }
}