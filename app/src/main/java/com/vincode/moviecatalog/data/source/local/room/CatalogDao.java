package com.vincode.moviecatalog.data.source.local.room;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;

import java.util.List;

@Dao
public interface CatalogDao {

    @WorkerThread
    @Query("SELECT * FROM movie")
    LiveData<List<MovieEntity>> getAllMovies();

    @WorkerThread
    @Query("SELECT * FROM movie WHERE favorite = 1")
    DataSource.Factory<Integer, MovieEntity> getFavMoviesAsPaged();

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<MovieEntity> getMovieDetail(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<MovieEntity> movies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovies(MovieEntity movie);


    @WorkerThread
    @Query("SELECT * FROM tvshow")
    LiveData<List<TvShowEntity>> getAllTvShows();

    @WorkerThread
    @Query("SELECT * FROM tvshow WHERE favorite = 1")
    DataSource.Factory<Integer, TvShowEntity> getFavTvShowAsPaged();

    @Query("SELECT * FROM tvshow WHERE id = :id")
    LiveData<TvShowEntity> getTvShowDetail(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShow(List<TvShowEntity> tvShows);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTvShow(TvShowEntity tvShow);


}
