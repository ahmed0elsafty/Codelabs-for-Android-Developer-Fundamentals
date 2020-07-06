package com.elsafty.simpleasynctask;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {
    WeakReference<TextView> mTextView;
    WeakReference<ProgressBar> mProgressBar;
    public SimpleAsyncTask(TextView textView, ProgressBar progressBar) {
        this.mTextView = new WeakReference<>(textView);
        this.mProgressBar = new WeakReference<>(progressBar);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... voids) {
        Random random = new Random();
        int n = random.nextInt(11);
        int s = n * 1000;
        for (int i = 1; i <= n; i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(s,(int) (((i) / (float) n) * 100));
        }

        return "Awake at last after sleeping for " + s/1000 + " seconds!";
    }

    @Override
    protected void onPostExecute(String s) {
        mTextView.get().setText(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mTextView.get().setText("Waiting Me " + values[0]/1000 + " s");
        mProgressBar.get().setProgress(values[1]);
    }
}
