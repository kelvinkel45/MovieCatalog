package com.vincode.moviecatalog.ui.favorite.tvshow;

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
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.ui.tvshow.DetailTvActivity;

public class FavoriteTvPagedAdapter extends PagedListAdapter<TvShowEntity, FavoriteTvPagedAdapter.TvViewHolder> {

    private final Context context;

    FavoriteTvPagedAdapter(Context context) {
        super(DIFF_CALLBACK);

        this.context = context;
    }

    private static DiffUtil.ItemCallback<TvShowEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<TvShowEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TvShowEntity oldItem, @NonNull TvShowEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull TvShowEntity oldItem, @NonNull TvShowEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        TvShowEntity tvShow = getItem(position);

        if (tvShow != null) {
            holder.tvTitle.setText(tvShow.getName());
            holder.tvYear.setText(tvShow.getReleaseDate());
            holder.tvLanguage.setText(tvShow.getLanguage());
            holder.tvRating.setText(tvShow.getRating());
            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185/" + tvShow.getPoster())
                    .into(holder.imgPoster);
            holder.cvClick.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailTvActivity.class);
                intent.putExtra(DetailTvActivity.EXTRA_TV, tvShow);
                context.startActivity(intent);
            });
        }
    }


    TvShowEntity getItemById(int swipedPosition) {
        return getItem(swipedPosition);
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
