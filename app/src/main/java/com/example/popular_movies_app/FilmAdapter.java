package com.example.popular_movies_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popular_movies_app.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MovieViewHolder> {

    private final List<Movie> mMovieList;

    private final RecyclerViewClickListener mListener;

    public interface RecyclerViewClickListener {

        void onClick(int position);
    }

    public FilmAdapter(RecyclerViewClickListener listener, List<Movie> movieList) {
        mListener = listener;
        mMovieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new MovieViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie movieObject = mMovieList.get(holder.getAdapterPosition());

        String posterPath =  "https://image.tmdb.org/t/p/w185" + movieObject.getMoviePoster();
        Picasso.get()
                .load(posterPath)
                .fit()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.movieListItemView);

        //holder.movieListItemView.setText(movieObject.getTitle());
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void updateMoviesList(List<Movie> movieList) {
        mMovieList.clear();
        mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        final ImageView movieListItemView;

        private final RecyclerViewClickListener mListener;

        MovieViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            movieListItemView = itemView.findViewById(R.id.iv_movie_icon);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mListener.onClick(adapterPosition);
        }

    }


}
