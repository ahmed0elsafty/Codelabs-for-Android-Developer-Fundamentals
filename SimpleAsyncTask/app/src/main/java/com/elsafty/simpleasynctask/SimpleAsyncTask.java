package com.elsafty.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

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
        mTextView.get().setText("Waiting Me " + values[0] + " ms");
    }
}
