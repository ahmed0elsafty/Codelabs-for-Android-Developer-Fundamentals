package com.elsafty.whowroteit;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String TAG = "MainActivity";
    private static final int LOADER_ID = 339;
    private static final String QUERY_BUNDLE = "input";
    private EditText mEdtBookInput;
    private TextView mTVBookTitle;
    private TextView mTVBookAuthors;
    private String mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtBookInput = findViewById(R.id.edt_book_titel);
        mTVBookTitle = findViewById(R.id.tv_book_title);
        mTVBookAuthors = findViewById(R.id.tv_book_authors);



        if (getSupportLoaderManager().getLoader(LOADER_ID) != null) {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }    }

    public void getBooks(View view) {
        mTVBookAuthors.setText("");
        mInput = mEdtBookInput.getText().toString().trim();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), inputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected() && mInput.length() != 0) {
            Bundle bundle = new Bundle();
            bundle.putString(QUERY_BUNDLE,mInput);
            getSupportLoaderManager().restartLoader(LOADER_ID,bundle,this);

            mTVBookTitle.setText("Loading...");
        } else {
            if (mInput.length() == 0) {
                mTVBookTitle.setText("Have no Input");
            } else {
                mTVBookTitle.setText("Network not available");
            }
        }


    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new BookLoader(this, bundle.getString(QUERY_BUNDLE));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String response) {
        try {
            JSONObject books = new JSONObject(response);
            JSONArray itemsArray = books.getJSONArray("items");
            JSONObject jsonObject = itemsArray.getJSONObject(1);
            JSONObject volumeInfo = jsonObject.getJSONObject("volumeInfo");
            String title = volumeInfo.getString("title");
            String authors = volumeInfo.getString("authors");
            mTVBookTitle.setText(title);
            mTVBookAuthors.setText(authors);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}