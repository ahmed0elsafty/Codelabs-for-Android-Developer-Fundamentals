package com.elsafty.whowroteit.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";
    private static final String MAX_RESULT_PARAM = "maxResults";
    private static final String PRINT_TYPE_PARAM = "printType";


    public static URL buildURI(String queryString) {
        Uri buildUri = Uri.parse(BOOK_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULT_PARAM, "10")
                .appendQueryParameter(PRINT_TYPE_PARAM, "books")
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponse(URL queryUrl) throws IOException {
        HttpURLConnection httpURLConnection = httpURLConnection = (HttpURLConnection) queryUrl.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            return getQueryString(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return null;
    }

    private static String getQueryString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");
        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            return null;
        }
    }
}
