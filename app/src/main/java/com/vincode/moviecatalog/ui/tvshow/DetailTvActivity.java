package com.vincode.moviecatalog.ui.tvshow;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.vincode.moviecatalog.R;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.utils.ViewModelFactory;

public class DetailTvActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";
    private TextView tvTitle, tvYear, tvRating, tvLanguage, tvDesc;
    private ImageView imgPoster, imgBackdrop;
    private ProgressBar progressBar;
    private ImageView imgFavoriteTvShow;
    private TvViewModel tvViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        Toolbar toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tv_title_tv);
        tvYear = findViewById(R.id.tv_year_tv);
        tvRating = findViewById(R.id.tv_rating_tv);
        tvLanguage = findViewById(R.id.tv_language_tv);
        tvDesc = findViewById(R.id.tv_detail_overview_tv);
        imgPoster = findViewById(R.id.img_poster_tv);
        progressBar = findViewById(R.id.progress_bar);
        imgBackdrop = findViewById(R.id.img_detail_backdrop);
        imgFavoriteTvShow = findViewById(R.id.img_detail_fav);

        tvViewModel = obtainViewModel();
        TvShowEntity tvShows = getIntent().getParcelableExtra(EXTRA_TV);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        if (tvShows != null) {
            progressBar.setVisibility(View.VISIBLE);

            tvViewModel.setTvShowId(tvShows.getId());
            tvViewModel.getTvShowDetail.observe(this, tvShowDetail -> {
                if (tvShowDetail != null) {

                    switch (tvShowDetail.status) {
                        case SUCCESS:
                            if (tvShowDetail.data != null) {
                                progressBar.setVisibility(View.GONE);
                                setTvShow(tvShows);
                                break;
                            }
                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(DetailTvActivity.this, "Ada Kesalahan Pada Aplikasi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(tvShows.getName());
            }

            setFavorites(tvShows);

        }


    }

    private TvViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(TvViewModel.class);
    }

    private void setTvShow(TvShowEntity tvShow) {
        tvTitle.setText(tvShow.getName());
        tvYear.setText(tvShow.getReleaseDate());
        tvRating.setText(tvShow.getRating());
        tvLanguage.setText(tvShow.getLanguage());
        tvDesc.setText(tvShow.getOverview());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/" + tvShow.getPoster())
                .into(imgPoster);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + tvShow.getBackdrop())
                .into(imgBackdrop);
    }

    private void setFavorites(TvShowEntity tvShowEntity) {
        if (tvShowEntity.isFavorite()) {
            imgFavoriteTvShow.setImageResource(R.drawable.ic_favorite);
        }

        tvViewModel.getTvShowDetail.observe(this, tvShow -> {
            if (tvShow != null) {
                switch (tvShow.status) {
                    case SUCCESS:
                        if (tvShow.data != null) {
                            progressBar.setVisibility(View.GONE);
                            boolean state = tvShow.data.isFavorite();
                            imgFavoriteTvShow.setOnClickListener(v -> {
                                if (state) {
                                    imgFavoriteTvShow.setImageResource(R.drawable.ic_favorite_border);
                                    Snackbar.make(imgFavoriteTvShow, "Film Anda telah dihapus dari favorit", Snackbar.LENGTH_LONG).show();
                                    tvViewModel.setFavoriteTvShow();
                                } else {
                                    imgFavoriteTvShow.setImageResource(R.drawable.ic_favorite);
                                    Snackbar.make(imgFavoriteTvShow, "Film Anda telah ditambahkan ke favorit", Snackbar.LENGTH_LONG).show();
                                    tvViewModel.setFavoriteTvShow();
                                }
                            });
                        }
                        break;
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(DetailTvActivity.this, "Ada Kesalahan Pada Aplikasi", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

}
