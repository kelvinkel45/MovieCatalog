package com.vincode.moviecatalog.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.vo.Resource;

import java.util.List;

interface CatalogDataSource {

    LiveData<Resource<List<MovieEntity>>> getAllMovies();

    LiveData<Resource<List<TvShowEntity>>> getAllTvShows();

    LiveData<Resource<MovieEntity>> getDetailMovie(long movieId);

    LiveData<Resource<TvShowEntity>> getDetailTvShow(long tvShowId);

    LiveData<Resource<PagedList<MovieEntity>>> getAllFavoriteMoviesPaged();

    LiveData<Resource<PagedList<TvShowEntity>>> getAllFavoriteTvShowsPaged();

    void setMoviesFavorite(MovieEntity movie, boolean state);

    void setFavoriteTvShow(TvShowEntity tvShow, boolean state);
}
