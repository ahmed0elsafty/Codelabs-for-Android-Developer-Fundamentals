package com.elsafty.whowroteit;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

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
    }

    public void getBooks(View view) {
        //mTVBookTitle.setText("");
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
            new FetchBooks(mTVBookTitle, mTVBookAuthors).execute(mInput);
            mTVBookTitle.setText("Loading...");
        } else {
            if (mInput.length() == 0) {
                mTVBookTitle.setText("Have no Input");
            } else {
                mTVBookTitle.setText("Network not available");
            }
        }

    }
}