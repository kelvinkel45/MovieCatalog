package com.vincode.moviecatalog.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.vincode.moviecatalog.data.source.local.LocalRepository;
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.data.source.remote.ApiResponse;
import com.vincode.moviecatalog.data.source.remote.RemoteRepository;
import com.vincode.moviecatalog.model.Movie;
import com.vincode.moviecatalog.model.TvShow;
import com.vincode.moviecatalog.utils.AppExecutors;
import com.vincode.moviecatalog.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class CatalogRepository implements CatalogDataSource {

    private final RemoteRepository remoteRepository;
    private final LocalRepository localRepository;
    private final AppExecutors appExecutors;
    private volatile static CatalogRepository INSTANCE = null;

    private CatalogRepository(@NonNull LocalRepository localRepository, @NonNull RemoteRepository remoteRepository, AppExecutors appExecutors) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
        this.appExecutors = appExecutors;
    }

    public static CatalogRepository getInstance(LocalRepository localRepository, RemoteRepository remoteRepository, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (CatalogRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CatalogRepository(localRepository, remoteRepository, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<List<MovieEntity>>> getAllMovies() {
        return new NetworkBoundResource<List<MovieEntity>, List<Movie>>(appExecutors) {

            @Override
            protected LiveData<List<MovieEntity>> loadFromDB() {
                return localRepository.getAllMovies();
            }

            @Override
            protected Boolean shouldFetch(List<MovieEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<Movie>>> createCall() {
                return remoteRepository.getMovieList();
            }

            @Override
            protected void saveCallResult(List<Movie> data) {
                List<MovieEntity> movieEntities = new ArrayList<>();

                for (Movie movie : data) {
                    movieEntities.add(new MovieEntity(
                            movie.getId(),
                            movie.getPoster(),
                            movie.getTitle(),
                            movie.getReleaseDate(),
                            movie.getOverview(),
                            movie.getLanguage(),
                            movie.getRating(),
                            movie.getBackdropPath(),
                            null));
                }
                localRepository.insertMovies(movieEntities);

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<TvShowEntity>>> getAllTvShows() {
        return new NetworkBoundResource<List<TvShowEntity>, List<TvShow>>(appExecutors) {

            @Override
            protected LiveData<List<TvShowEntity>> loadFromDB() {
                return localRepository.getAllTvShows();
            }

            @Override
            protected Boolean shouldFetch(List<TvShowEntity> data) {
                return (data == null) || (data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<TvShow>>> createCall() {
                return remoteRepository.getTvShowList();
            }

            @Override
            protected void saveCallResult(List<TvShow> data) {
                List<TvShowEntity> tvShowEntities = new ArrayList<>();

                for (TvShow tvShow : data) {
                    tvShowEntities.add(new TvShowEntity(
                            tvShow.getId(),
                            tvShow.getPoster(),
                            tvShow.getName(),
                            tvShow.getReleaseDate(),
                            tvShow.getOverview(),
                            tvShow.getLanguage(),
                            tvShow.getRating(),
                            tvShow.getBackdrop(),
                            null));
                }
                localRepository.insertTvShow(tvShowEntities);

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<MovieEntity>> getDetailMovie(long movieId) {
        return new NetworkBoundResource<MovieEntity, Movie>(appExecutors) {

            @Override
            protected LiveData<MovieEntity> loadFromDB() {
                return localRepository.getMovieDetail(movieId);
            }

            @Override
            protected Boolean shouldFetch(MovieEntity data) {
                return (data == null);
            }

            @Override
            protected LiveData<ApiResponse<Movie>> createCall() {
                return remoteRepository.getDetailMovie(movieId);
            }

            @Override
            protected void saveCallResult(Movie data) {
                MovieEntity movieEntity = new MovieEntity(data.getId(),
                        data.getPoster(),
                        data.getTitle(),
                        data.getReleaseDate(),
                        data.getOverview(),
                        data.getLanguage(),
                        data.getRating(),
                        data.getBackdropPath(),
                        null);
                List<MovieEntity> movieEntities = new ArrayList<>();
                movieEntities.add(movieEntity);
                localRepository.insertMovies(movieEntities);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<TvShowEntity>> getDetailTvShow(long tvShowId) {
        return new NetworkBoundResource<TvShowEntity, TvShow>(appExecutors) {

            @Override
            protected LiveData<TvShowEntity> loadFromDB() {
                return localRepository.getTvShowDetail(tvShowId);
            }

            @Override
            protected Boolean shouldFetch(TvShowEntity data) {
                return (data == null);
            }

            @Override
            protected LiveData<ApiResponse<TvShow>> createCall() {
                return remoteRepository.getDetailTv(tvShowId);
            }

            @Override
            protected void saveCallResult(TvShow data) {
                TvShowEntity tvShowEntity = new TvShowEntity(data.getId(),
                        data.getPoster(),
                        data.getName(),
                        data.getReleaseDate(),
                        data.getOverview(),
                        data.getLanguage(),
                        data.getRating(),
                        data.getBackdrop(),
                        null);
                List<TvShowEntity> tvShowEntities = new ArrayList<>();
                tvShowEntities.add(tvShowEntity);
                localRepository.insertTvShow(tvShowEntities);
            }
        }.asLiveData();
    }

    @Override
    public void setMoviesFavorite(MovieEntity movieEntity, boolean state) {
        Runnable runnable = () -> localRepository.setMovieFavorite(movieEntity, state);
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void setFavoriteTvShow(TvShowEntity tvShowEntity, boolean state) {
        Runnable runnable = () -> localRepository.setTVFavorite(tvShowEntity, state);
        appExecutors.diskIO().execute(runnable);

    }

    @Override
    public LiveData<Resource<PagedList<MovieEntity>>> getAllFavoriteMoviesPaged() {
        return new NetworkBoundResource<PagedList<MovieEntity>, List<Movie>>(appExecutors) {

            @Override
            protected LiveData<PagedList<MovieEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(localRepository.getFavMoviesPaged(), 20).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<MovieEntity> data) {
                return false;
            }


            @Override
            protected LiveData<ApiResponse<List<Movie>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<Movie> data) {

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<TvShowEntity>>> getAllFavoriteTvShowsPaged() {
        return new NetworkBoundResource<PagedList<TvShowEntity>, List<TvShow>>(appExecutors) {

            @Override
            protected LiveData<PagedList<TvShowEntity>> loadFromDB() {
                return new LivePagedListBuilder<>(localRepository.getFavTvShowsPaged(), 20).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<TvShowEntity> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<List<TvShow>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<TvShow> data) {

            }
        }.asLiveData();
    }
}
