package com.vincode.moviecatalog.data.source.remote.response;

import com.google.gson.annotations.SerializedName;
import com.vincode.moviecatalog.model.Movie;

import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

}
