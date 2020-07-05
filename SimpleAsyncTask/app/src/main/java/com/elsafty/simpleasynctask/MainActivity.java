package com.elsafty.simpleasynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView);
        mButton = findViewById(R.id.button);
    }

    public void startTask(View view) {
        mTextView.setText(null);
        new SimpleAsyncTask(mTextView).execute();
    }


    class SimpleAsyncTask extends AsyncTask<Void, Void, String> {
        WeakReference<TextView> mTextView;

        public SimpleAsyncTask(TextView textView) {
            this.mTextView = new WeakReference<>(textView);
        }

        @Override
        protected String doInBackground(Void... voids) {
            Random random = new Random();
            int n = random.nextInt(11);
            int s = n * 200;
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
    }
}