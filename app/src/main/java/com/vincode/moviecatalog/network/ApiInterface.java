package com.vincode.moviecatalog.network;

import com.vincode.moviecatalog.data.source.remote.response.MovieResponse;
import com.vincode.moviecatalog.data.source.remote.response.TvShowResponse;
import com.vincode.moviecatalog.model.Movie;
import com.vincode.moviecatalog.model.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<MovieResponse> getListMovie(
            @Query("api_key") String apikey,
            @Query("language") String language
    );

    @GET("discover/tv")
    Call<TvShowResponse> getListTvShow(
            @Query("api_key") String apikey,
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Call<Movie> getDetailMovie(
            @Path("movie_id") long movie_id,
            @Query("api_key") String apikey,
            @Query("language") String language
    );

    @GET("movie/{tv_id}")
    Call<TvShow> getDetailTv(
            @Path("tv_id") long tv_id,
            @Query("api_key") String apikey,
            @Query("language") String language
    );

}
