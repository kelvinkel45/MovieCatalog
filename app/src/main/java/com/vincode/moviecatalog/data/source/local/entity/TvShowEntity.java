package com.vincode.moviecatalog.data.source.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tvshow")
public class TvShowEntity implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private long id;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String poster;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
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
    private String backdrop;

    @ColumnInfo(name = "favorite")
    private boolean favorite = false;


    public TvShowEntity(long id, String poster, String name, String releaseDate, String overview, String language, String rating, String backdrop, Boolean favorite) {
        this.id = id;
        this.poster = poster;
        this.name = name;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.language = language;
        this.rating = rating;
        this.backdrop = backdrop;
        if (favorite != null) {
            this.favorite = favorite;
        }
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

    public String getBackdrop() {
        return backdrop;
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
        dest.writeString(this.name);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeString(this.language);
        dest.writeString(this.rating);
        dest.writeString(this.backdrop);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
    }

    protected TvShowEntity(Parcel in) {
        this.id = in.readLong();
        this.poster = in.readString();
        this.name = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.language = in.readString();
        this.rating = in.readString();
        this.backdrop = in.readString();
        this.favorite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<TvShowEntity> CREATOR = new Parcelable.Creator<TvShowEntity>() {
        @Override
        public TvShowEntity createFromParcel(Parcel source) {
            return new TvShowEntity(source);
        }

        @Override
        public TvShowEntity[] newArray(int size) {
            return new TvShowEntity[size];
        }
    };
}
