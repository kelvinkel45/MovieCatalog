package com.vincode.moviecatalog.data.source.remote;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vincode.moviecatalog.BuildConfig;
import com.vincode.moviecatalog.data.source.remote.response.MovieResponse;
import com.vincode.moviecatalog.data.source.remote.response.TvShowResponse;
import com.vincode.moviecatalog.model.Movie;
import com.vincode.moviecatalog.model.TvShow;
import com.vincode.moviecatalog.network.ApiInterface;
import com.vincode.moviecatalog.utils.EspressoIdlingResource;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemoteRepository {

    private static RemoteRepository INSTANCE;
    private ApiInterface apiInterface;
    private static final String LANGUAGE = "en-US";
    private final long SERVICE_LATENCY_IN_MILLIS = 2000;

    private RemoteRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public static RemoteRepository getInstance(ApiInterface apiInterface) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRepository(apiInterface);
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<List<Movie>>> getMovieList() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<Movie>>> resultMovies = new MutableLiveData<>();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Call<MovieResponse> call = apiInterface.getListMovie(BuildConfig.TMDB_API_KEY, LANGUAGE);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    if (response.body() != null) {
                        resultMovies.setValue(ApiResponse.success(response.body().getMovies()));
                        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                            EspressoIdlingResource.decrement();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                    EspressoIdlingResource.decrement();
                }
            });

        }, SERVICE_LATENCY_IN_MILLIS);

        return resultMovies;
    }

    public LiveData<ApiResponse<List<TvShow>>> getTvShowList() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<TvShow>>> resultTvShow = new MutableLiveData<>();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Call<TvShowResponse> call = apiInterface.getListTvShow(BuildConfig.TMDB_API_KEY, LANGUAGE);
            call.enqueue(new Callback<TvShowResponse>() {
                @Override
                public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                    if (response.body() != null) {
                        resultTvShow.setValue(ApiResponse.success(response.body().getTvShows()));
                        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                            EspressoIdlingResource.decrement();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TvShowResponse> call, @NotNull Throwable t) {
                    EspressoIdlingResource.decrement();
                }
            });

        }, SERVICE_LATENCY_IN_MILLIS);

        return resultTvShow;
    }

    public LiveData<ApiResponse<Movie>> getDetailMovie(long movieId) {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<Movie>> resultMovie = new MutableLiveData<>();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Call<Movie> call = apiInterface.getDetailMovie(movieId, BuildConfig.TMDB_API_KEY, LANGUAGE);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                    resultMovie.setValue(ApiResponse.success(response.body()));
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {
                    EspressoIdlingResource.decrement();
                }
            });

        }, SERVICE_LATENCY_IN_MILLIS);
        return resultMovie;
    }

    public LiveData<ApiResponse<TvShow>> getDetailTv(long tvShowId) {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<TvShow>> resultTvShow = new MutableLiveData<>();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Call<TvShow> call = apiInterface.getDetailTv(tvShowId, BuildConfig.TMDB_API_KEY, LANGUAGE);
            call.enqueue(new Callback<TvShow>() {
                @Override
                public void onResponse(@NotNull Call<TvShow> call, @NotNull Response<TvShow> response) {
                    resultTvShow.setValue(ApiResponse.success(response.body()));
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TvShow> call, @NotNull Throwable t) {
                    EspressoIdlingResource.decrement();
                }
            });

        }, SERVICE_LATENCY_IN_MILLIS);
        return resultTvShow;
    }

}
