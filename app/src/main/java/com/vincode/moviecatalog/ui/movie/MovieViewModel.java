package com.vincode.moviecatalog.ui.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.vo.Resource;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private CatalogRepository catalogRepository;
    private MutableLiveData<Long> movieId = new MutableLiveData<>();
    private MutableLiveData<String> mLosin = new MutableLiveData<>();

    public MovieViewModel(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public LiveData<Resource<List<MovieEntity>>> movies = Transformations.switchMap(mLosin,
            data -> catalogRepository.getAllMovies());

    LiveData<Resource<MovieEntity>> getMovieDetail = Transformations.switchMap(movieId,
            movieId -> catalogRepository.getDetailMovie(movieId));


    void setUsername(String username) {
        mLosin.setValue(username);
    }

    void setMovieId(long movieId) {
        this.movieId.setValue(movieId);
    }

    void setFavoriteMovie() {
        if (getMovieDetail.getValue() != null) {
            MovieEntity movieEntity = getMovieDetail.getValue().data;

            if (movieEntity != null) {
                final boolean state = !movieEntity.isFavorite();
                catalogRepository.setMoviesFavorite(movieEntity, state);
            }
        }
    }
}
