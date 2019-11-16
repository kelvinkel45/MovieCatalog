package com.vincode.moviecatalog.data.source.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie")
public class MovieEntity implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private long id;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String poster;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String language;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private String rating;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "favorite")
    private boolean favorite = false;

    public MovieEntity(long id, String poster, String title, String releaseDate, String overview, String language, String rating, String backdropPath, Boolean favorite) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.language = language;
        this.rating = rating;
        this.backdropPath = backdropPath;
        if (favorite != null) {
            this.favorite = favorite;
        }
    }

//    public MovieEntity(Movie movie) {
//    }

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
        return releaseDate;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
    }

    public MovieEntity(Parcel in) {
        this.id = in.readLong();
        this.poster = in.readString();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.language = in.readString();
        this.rating = in.readString();
        this.backdropPath = in.readString();
        this.favorite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MovieEntity> CREATOR = new Parcelable.Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel source) {
            return new MovieEntity(source);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };
}
