package com.vincode.moviecatalog.ui.tvshow;

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
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;

import java.util.ArrayList;
import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {

    private final Activity activity;
    private final List<TvShowEntity> tvShowList = new ArrayList<>();

    TvShowAdapter(Activity activity) {
        this.activity = activity;
    }

    void setTvShowList(List<TvShowEntity> listTvShows) {
        if (listTvShows == null) return;
        this.tvShowList.clear();
        this.tvShowList.addAll(listTvShows);
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        TvShowEntity tvShow = tvShowList.get(position);

        holder.tvTitle.setText(tvShow.getName());
        holder.tvYear.setText(tvShow.getReleaseDate());
        holder.tvLanguage.setText(tvShow.getLanguage());
        holder.tvRating.setText(tvShow.getRating());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185/" + tvShow.getPoster())
                .into(holder.imgPoster);
        holder.cvClick.setOnClickListener(view -> {
            Intent intent = new Intent(activity, DetailTvActivity.class);
            intent.putExtra(DetailTvActivity.EXTRA_TV, tvShow);
            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgPoster;
        final TextView tvTitle, tvYear, tvRating, tvLanguage;
        final CardView cvClick;

        TvViewHolder(@NonNull View itemView) {
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
