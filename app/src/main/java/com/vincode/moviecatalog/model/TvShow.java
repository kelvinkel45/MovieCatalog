package com.vincode.moviecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvShow implements Parcelable {
    @SerializedName("id")
    private long id;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("name")
    private String name;

    @SerializedName("first_air_date")
    private String releaseDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String language;

    @SerializedName("vote_average")
    private String rating;

    @SerializedName("backdrop_path")
    private String backdrop;

    public TvShow(long id, String poster, String name, String releaseDate, String overview, String language, String rating, String backdrop) {
        this.id = id;
        this.poster = poster;
        this.name = name;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.language = language;
        this.rating = rating;
        this.backdrop = backdrop;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBackdrop() {
        return backdrop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.name);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeString(this.language);
        dest.writeString(this.rating);
        dest.writeString(this.backdrop);
    }

    protected TvShow(Parcel in) {
        this.id = in.readLong();
        this.poster = in.readString();
        this.name = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.language = in.readString();
        this.rating = in.readString();
        this.backdrop = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
