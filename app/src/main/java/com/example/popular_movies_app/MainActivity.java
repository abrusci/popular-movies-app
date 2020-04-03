package com.example.popular_movies_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popular_movies_app.model.Movie;
import com.example.popular_movies_app.utils.JsonUtils;
import com.example.popular_movies_app.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements FilmAdapter.RecyclerViewClickListener {

    private RecyclerView mainMovieDisplay;

    private List<Movie> movieList;

    private ProgressBar mLoadingIndicator;

    private TextView mErrorMessageDisplay;

    private FilmAdapter filmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMovieDisplay = findViewById(R.id.movie_display);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL,false);
        mainMovieDisplay.setLayoutManager(layoutManager);

        mainMovieDisplay.setHasFixedSize(true);

        movieList = new ArrayList<>();

        filmAdapter = new FilmAdapter(this, movieList);
        mainMovieDisplay.setAdapter(filmAdapter);

        movieSearchQuery("MOST_POPULAR");

    }

    private void movieSearchQuery(String searchCase) {
        URL movieSearchQuery = NetworkUtils.buildUrl(this, searchCase);
        new MovieQueryTask().execute(movieSearchQuery);
    }

    private void showErrorMessage() {
        mainMovieDisplay.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showMovieDataView() {
        mainMovieDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(int position) {
        Context context = MainActivity.this;
        Class destinationActivity = DetailedActivity.class;
        Intent detailedViewIntent = new Intent(context, destinationActivity);
        detailedViewIntent.putExtra("movieTitle", movieList.get(position).getTitle());
        detailedViewIntent.putExtra("moviePoster", movieList.get(position).getMoviePoster());
        detailedViewIntent.putExtra("overview", movieList.get(position).getOverview());
        detailedViewIntent.putExtra("voteAverage", movieList.get(position).getVoteAverage());
        detailedViewIntent.putExtra("releaseDate", movieList.get(position).getReleaseDate());
        startActivity(detailedViewIntent);
    }

    class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieSearchResults = null;
            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                try {
                    movieList = JsonUtils.parseMovieJson(movieSearchResults);
                    filmAdapter.updateMoviesList(movieList);
                    showMovieDataView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.most_popular) {
            movieSearchQuery("MOST_POPULAR");
            return true;
        }
        if (itemThatWasClickedId == R.id.top_rated) {
            movieSearchQuery("TOP_RATED");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
