package com.elsafty.whowroteit;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.elsafty.whowroteit.Utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class BookLoader extends AsyncTaskLoader<String> {
    private String input;

    public BookLoader(@NonNull Context context, String input) {
        super(context);
        this.input = input;
    }

    @Nullable
    @Override
    public String loadInBackground() {

        if(input==null){
            return null;
        }
        URL url = NetworkUtils.buildURI(input);
        String response = null;
        try {
            response = NetworkUtils.getResponse(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
