package com.example.popular_movies_app.utils;

import android.content.Context;
import android.net.Uri;

import com.example.popular_movies_app.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class NetworkUtils {

    private final static String MOST_POPULAR_BASE_URL =
            "https://api.themoviedb.org/3/movie/popular";

    private final static String TOP_RATED_BASE_URL =
            "https://api.themoviedb.org/3/movie/top_rated";

    private final static String PARAM_QUERY = "api_key";


    public static URL buildUrl(Context context, String searchQuery) {

        String baseURL;

        if(Objects.equals(searchQuery, "TOP_RATED")){
            baseURL = NetworkUtils.TOP_RATED_BASE_URL;
        } else{
            baseURL = NetworkUtils.MOST_POPULAR_BASE_URL;
        }

        String api_key = context.getString(R.string.TMDB_API_TOKEN);

        Uri builtUri = Uri.parse(baseURL).buildUpon()
            .appendQueryParameter(PARAM_QUERY, api_key)
            .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
