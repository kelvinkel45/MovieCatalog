package com.vincode.moviecatalog.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.data.source.local.room.CatalogDao;

import java.util.List;

public class LocalRepository {

    private final CatalogDao catalogDao;

    private LocalRepository(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    private static LocalRepository INSTANCE;

    public static LocalRepository getINSTANCE(CatalogDao catalogDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalRepository(catalogDao);
        }
        return INSTANCE;
    }

    public LiveData<List<MovieEntity>> getAllMovies() {
        return catalogDao.getAllMovies();
    }

    public DataSource.Factory<Integer, MovieEntity> getFavMoviesPaged() {
        return catalogDao.getFavMoviesAsPaged();
    }

    public LiveData<MovieEntity> getMovieDetail(long id) {
        return catalogDao.getMovieDetail(id);
    }

    public void insertMovies(List<MovieEntity> movieEntities) {
        catalogDao.insertMovies(movieEntities);
    }

    public void setMovieFavorite(MovieEntity movieEntity, boolean state) {
        movieEntity.setFavorite(state);
        catalogDao.updateMovies(movieEntity);

    }

    public LiveData<List<TvShowEntity>> getAllTvShows() {
        return catalogDao.getAllTvShows();
    }

    public DataSource.Factory<Integer, TvShowEntity> getFavTvShowsPaged() {
        return catalogDao.getFavTvShowAsPaged();
    }

    public LiveData<TvShowEntity> getTvShowDetail(long id) {
        return catalogDao.getTvShowDetail(id);
    }

    public void insertTvShow(List<TvShowEntity> tvShowEntities) {
        catalogDao.insertTvShow(tvShowEntities);
    }

    public void setTVFavorite(TvShowEntity tvShowEntity, boolean state) {
        tvShowEntity.setFavorite(state);
        catalogDao.updateTvShow(tvShowEntity);
    }


}
