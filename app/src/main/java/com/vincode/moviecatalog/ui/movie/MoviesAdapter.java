package com.vincode.moviecatalog.ui.movie;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vincode.moviecatalog.R;
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final Activity activity;
    private final List<MovieEntity> movieList = new ArrayList<>();

    MoviesAdapter(Activity activity) {
        this.activity = activity;
    }

    void setMovieList(List<MovieEntity> listMovies) {
        if (listMovies == null) return;
        this.movieList.clear();
        this.movieList.addAll(listMovies);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final MovieEntity movie = movieList.get(position);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvYear.setText(movie.getReleaseDate());
        holder.tvRating.setText(movie.getRating());
        holder.tvLanguage.setText(movie.getLanguage());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster())
                .into(holder.imgPoster);
        holder.cvClick.setOnClickListener(view -> {
            Intent intent = new Intent(activity, DetailMovieActivity.class);
            intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgPoster;
        final TextView tvTitle, tvYear, tvRating, tvLanguage;
        final CardView cvClick;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvLanguage = itemView.findViewById(R.id.tv_language);
            cvClick = itemView.findViewById(R.id.cv_item_movies);
        }
    }
}
