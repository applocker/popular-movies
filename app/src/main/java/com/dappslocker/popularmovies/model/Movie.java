package com.dappslocker.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favouriteMovies")
public class Movie implements Parcelable{

    @PrimaryKey
    @ColumnInfo(name = "movieId")
    @SerializedName("id")
    private int mMovieID;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String mTitle;

    @Ignore
    @SerializedName("poster_path")
    private String mPosterUrl;

    @Ignore
    @SerializedName("overview")
    private String mOverview;

    @Ignore
    @SerializedName("popularity")
    private Double mRating;

    @Ignore
    @SerializedName("release_date")
    private String mReleaseDate;

    @Ignore
    public Movie(int mMovieID, String mTitle, String mPosterUrl,String mOverview, Double mRating,String mReleaseDate ){
        this.mMovieID = mMovieID;
        this.mTitle = mTitle;
        this.mPosterUrl = mPosterUrl;
        this.mOverview = mOverview;
        this.mRating = mRating;
        this.mReleaseDate = mReleaseDate;
    }
    /**
     * Constructor used by Room to save and retrieve favourite movies
    */
    public Movie(int mMovieID, String mTitle){
        this.mMovieID = mMovieID;
        this.mTitle = mTitle;
    }

    public int getMovieID() {
        return mMovieID;
    }

    public void setMovieID(int mMovieID) {
        this.mMovieID = mMovieID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setPosterUrl(String mPosterUrl) {
        this.mPosterUrl = mPosterUrl;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(Double mRating) {
        this.mRating = mRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMovieID);
        dest.writeString(mTitle);
        dest.writeString(mPosterUrl);
        dest.writeString(mOverview);
        dest.writeDouble(mRating);
        dest.writeString(mReleaseDate);
    }

    private Movie(Parcel in) {
        if (in.readByte() == 0) {
            mMovieID = 0;
        } else {
            mMovieID = in.readInt();
        }
        mTitle = in.readString();
        mPosterUrl = in.readString();
        mOverview = in.readString();
        if (in.readByte() == 0) {
            mRating = null;
        } else {
            mRating = in.readDouble();
        }
        mReleaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
