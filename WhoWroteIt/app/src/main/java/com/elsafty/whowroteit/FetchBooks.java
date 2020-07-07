package com.elsafty.whowroteit;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.elsafty.whowroteit.Utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

public class FetchBooks extends AsyncTask<String, Void, String> {
    private static final String TAG = "FetchBooks";
    WeakReference<TextView> mBookTitleTextView;
    WeakReference<TextView> mBookAuthorTextView;

    public FetchBooks(TextView bookTitleTextView, TextView bookAuthorTextView) {
        this.mBookTitleTextView = new WeakReference<>(bookTitleTextView);
        this.mBookAuthorTextView = new WeakReference<>(bookAuthorTextView);
    }

    @Override
    protected String doInBackground(String... strings) {
        if(strings[0]==null){
            return null;
        }
        URL url = NetworkUtils.buildURI(strings[0]);
        Log.d(TAG, "doInBackground: full Url = " + url);
        String response = null;
        try {
            response = NetworkUtils.getResponse(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /*@Override
    protected void onCancelled(String response) {
        super.onCancelled(response);
        String title = null;
        String mAuthors = null;
        try {
            JSONObject books = new JSONObject(response);
            JSONArray itemsArray = books.getJSONArray("items");
            JSONObject jsonObject = itemsArray.getJSONObject(1);
            JSONObject volumeInfo = jsonObject.getJSONObject("volumeInfo");
            title = volumeInfo.getString("title");
            mAuthors = volumeInfo.getString("authors");
            mBookTitleTextView.get().setText(title);
            mBookAuthorTextView.get().setText(mAuthors);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        String title = null;
        String mAuthors = null;
        try {
            JSONObject books = new JSONObject(response);
            JSONArray itemsArray = books.getJSONArray("items");
            JSONObject jsonObject = itemsArray.getJSONObject(1);
            JSONObject volumeInfo = jsonObject.getJSONObject("volumeInfo");
            title = volumeInfo.getString("title");
            mAuthors = volumeInfo.getString("authors");
            mBookTitleTextView.get().setText(title);
            mBookAuthorTextView.get().setText(mAuthors);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
