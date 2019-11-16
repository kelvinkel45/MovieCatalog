package com.vincode.moviecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    @SerializedName("id")
    private long id;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String language;

    @SerializedName("vote_average")
    private String rating;

    @SerializedName("backdrop_path")
    private String backdropPath;


    public Movie(long id, String poster, String title, String releaseDate, String overview, String language, String rating, String backdropPath) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.language = language;
        this.rating = rating;
        this.backdropPath = backdropPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate.substring(0, 4);
    }

    public String getOverview() {
        return overview;
    }

    public String getLanguage() {
        return language;
    }

    public String getRating() {
        return rating;
    }

    public String getBackdropPath() {
        return backdropPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeString(this.language);
        dest.writeString(this.rating);
        dest.writeString(this.backdropPath);
    }

    protected Movie(Parcel in) {
        this.id = in.readLong();
        this.poster = in.readString();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.language = in.readString();
        this.rating = in.readString();
        this.backdropPath = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
