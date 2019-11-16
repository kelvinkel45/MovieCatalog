package com.vincode.moviecatalog.data.source.remote.response;

import com.google.gson.annotations.SerializedName;
import com.vincode.moviecatalog.model.TvShow;

import java.util.List;

public class TvShowResponse {

    @SerializedName("results")
    private List<TvShow> tvShows;

    public List<TvShow> getTvShows() {
        return tvShows;
    }

}
