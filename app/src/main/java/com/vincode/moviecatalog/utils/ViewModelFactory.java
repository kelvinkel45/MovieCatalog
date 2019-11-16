package com.vincode.moviecatalog.utils;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.di.Injection;
import com.vincode.moviecatalog.ui.favorite.movie.FavoriteMovieViewModel;
import com.vincode.moviecatalog.ui.favorite.tvshow.FavoriteTvViewModel;
import com.vincode.moviecatalog.ui.movie.MovieViewModel;
import com.vincode.moviecatalog.ui.tvshow.TvViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final CatalogRepository catalogRepository;

    private ViewModelFactory(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.catalogRepository(application));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            //noinspection unchecked
            return (T) new MovieViewModel(catalogRepository);

        } else if (modelClass.isAssignableFrom(TvViewModel.class)) {
            //noinspection unchecked
            return (T) new TvViewModel(catalogRepository);

        } else if (modelClass.isAssignableFrom(FavoriteMovieViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoriteMovieViewModel(catalogRepository);

        } else if (modelClass.isAssignableFrom(FavoriteTvViewModel.class)) {
            //noinspection unchecked
            return (T) new FavoriteTvViewModel(catalogRepository);
        }

        throw new IllegalArgumentException("Unknown Viewmodel class : " + modelClass.getName());
    }
}
