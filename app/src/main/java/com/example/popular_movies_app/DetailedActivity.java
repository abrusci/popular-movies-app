package com.example.popular_movies_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView posterIv = findViewById(R.id.poster_image_iv);
        TextView overviewTextView = findViewById(R.id.overview_tv);
        TextView voteAverageTextView = findViewById(R.id.vote_average_tv);
        TextView releaseDateTextView = findViewById(R.id.release_date_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        String posterPath =  "https://image.tmdb.org/t/p/w185" + Objects.requireNonNull(getIntent().getExtras()).get("moviePoster");

        Picasso.get()
                .load(posterPath)
                .into(posterIv);

        String movieTitle = getIntent().getExtras().getString("movieTitle");
        String overview = getIntent().getExtras().getString("overview");
        String voteAverage = getIntent().getExtras().getString("voteAverage");
        String releaseDate = getIntent().getExtras().getString("releaseDate");

        setTitle(movieTitle);
        voteAverageTextView.setText(voteAverage);
        overviewTextView.setText(overview);
        releaseDateTextView.setText(releaseDate);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }

}
