package com.vincode.moviecatalog.ui.favorite.movie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vincode.moviecatalog.R;
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.ui.movie.DetailMovieActivity;

public class FavoriteMoviePagedAdapter extends PagedListAdapter<MovieEntity, FavoriteMoviePagedAdapter.MovieViewHolder> {

    private final Context context;

    FavoriteMoviePagedAdapter(Context context) {
        super(DIFF_CALLBACK);

        this.context = context;

    }

    private static DiffUtil.ItemCallback<MovieEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull MovieEntity oldItem, @NonNull MovieEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final MovieEntity movie = getItem(position);

        if (movie != null) {
            holder.tvTitle.setText(movie.getTitle());
            holder.tvYear.setText(movie.getReleaseDate());
            holder.tvRating.setText(movie.getRating());
            holder.tvLanguage.setText(movie.getLanguage());
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185/" + movie.getPoster())
                    .into(holder.imgPoster);
            holder.cvClick.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
                context.startActivity(intent);
            });
        }

    }


    MovieEntity getItemById(int swipedPosition) {
        return getItem(swipedPosition);
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
