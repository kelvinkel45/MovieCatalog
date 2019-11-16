package com.vincode.moviecatalog.ui.movie;

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
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.utils.ViewModelFactory;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private TextView tvTitle, tvYear, tvRating, tvLanguage, tvDesc;
    private ImageView imgPoster, imgBackdrop;
    private ProgressBar progressBar;
    private ImageView imgFavorite;
    private MovieViewModel movieViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        progressBar = findViewById(R.id.progress_bar);
        tvTitle = findViewById(R.id.tv_title);
        tvYear = findViewById(R.id.tv_year);
        tvRating = findViewById(R.id.tv_rating);
        tvLanguage = findViewById(R.id.tv_language);
        tvDesc = findViewById(R.id.tv_detail_overview_movie);
        imgPoster = findViewById(R.id.img_poster);
        imgBackdrop = findViewById(R.id.img_detail_backdrop);
        imgFavorite = findViewById(R.id.img_detail_fav);

        movieViewModel = obtainViewModel();
        MovieEntity movies = getIntent().getParcelableExtra(EXTRA_MOVIE);


        if (movies != null) {
            progressBar.setVisibility(View.VISIBLE);

            movieViewModel.setMovieId(movies.getId());
            movieViewModel.getMovieDetail.observe(this, movieDetail -> {
                if (movieDetail != null) {

                    switch (movieDetail.status) {
                        case SUCCESS:
                            if (movieDetail.data != null) {
                                progressBar.setVisibility(View.GONE);
                                setMovie(movies);
                                break;
                            }
                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(DetailMovieActivity.this, "Ada Kesalahan Pada Aplikasi", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(movies.getTitle());
            }

            setFavorites(movies);
        }


    }

    private MovieViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MovieViewModel.class);
    }

    private void setMovie(MovieEntity movies) {
        tvTitle.setText(movies.getTitle());
        tvYear.setText(movies.getReleaseDate());
        tvRating.setText(movies.getRating());
        tvLanguage.setText(movies.getLanguage());
        tvDesc.setText(movies.getOverview());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/" + movies.getPoster())
                .into(imgPoster);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/" + movies.getBackdropPath())
                .into(imgBackdrop);

    }

    private void setFavorites(MovieEntity movieEntity) {

        if (movieEntity.isFavorite()) {
            imgFavorite.setImageResource(R.drawable.ic_favorite);
        }

        movieViewModel.getMovieDetail.observe(this, movie -> {
            if (movie != null) {
                switch (movie.status) {
                    case SUCCESS:
                        if (movie.data != null) {
                            progressBar.setVisibility(View.GONE);
                            boolean state = movie.data.isFavorite();

                            imgFavorite.setOnClickListener(v -> {
                                if (state) {
                                    imgFavorite.setImageResource(R.drawable.ic_favorite_border);
                                    Snackbar.make(imgFavorite, "Film Anda telah dihapus dari favorit", Snackbar.LENGTH_LONG).show();
                                    movieViewModel.setFavoriteMovie();
                                } else {
                                    imgFavorite.setImageResource(R.drawable.ic_favorite);
                                    Snackbar.make(imgFavorite, "Film Anda telah ditambahkan ke favorit", Snackbar.LENGTH_LONG).show();
                                    movieViewModel.setFavoriteMovie();
                                }
                            });
                        }
                        break;
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(DetailMovieActivity.this, "Ada Kesalahan Pada Aplikasi", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

//        if(databaseHelper.isFavorites(movie.getId())){
//            imgFavMovie.setImageResource(R.drawable.ic_favorite_red_24dp);
//        }


    }

}
