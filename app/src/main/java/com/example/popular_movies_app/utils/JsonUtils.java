package com.example.popular_movies_app.utils;

import com.example.popular_movies_app.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Movie> parseMovieJson(String json) throws JSONException {

        //Initialising movie and JSON objects
        List<Movie> movieList = new ArrayList<>();
        JSONObject moviesJson = new JSONObject(json);
        JSONArray resultsArray =  moviesJson.getJSONArray("results");

        for(int i = 0; i < resultsArray.length(); i++) {

            Movie movie = new Movie();

            String resultsString = resultsArray.get(i).toString();
            JSONObject movieJson = new JSONObject(resultsString);

            //parsing JSON object
            String title = movieJson.getString("title");
            String moviePoster = movieJson.getString("poster_path");
            String overview = movieJson.getString("overview");
            String voteAverage = movieJson.getString("vote_average");
            String releaseDate = movieJson.getString("release_date");

            //populating movie object
            movie.setTitle(title);
            movie.setMoviePoster(moviePoster);
            movie.setOverview(overview);
            movie.setVoteAverage(voteAverage);
            movie.setReleaseDate(releaseDate);

            movieList.add(i, movie);
        }

        return movieList;
    }
}
