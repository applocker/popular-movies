package com.dappslocker.popularmovies.model;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Tiwuya on 03/07/2018.
 */

public class Movie implements Parcelable{
    private Integer mMovieID;
    private String mTitle;
    private String mPosterUrl;
    private String mOverview;
    private Double mRating;
    private String mReleaseDate;
    private Drawable mPosterImage;
    public Drawable getPosterImage() {
        return mPosterImage;
    }

    public void setPosterImage(Drawable mPosterImage) {
        this.mPosterImage = mPosterImage;
    }



    public Movie(int mMovieID, String mTitle, String mPosterUrl,String mOverview, Double mRating,String mReleaseDate ){
        this.mMovieID = mMovieID;
        this.mTitle = mTitle;
        this.mPosterUrl = mPosterUrl;
        this.mOverview = mOverview;
        this.mRating = mRating;
        this.mReleaseDate = mReleaseDate;
        this.mPosterImage = null;
    }

    public int getmMovieID() {
        return mMovieID;
    }

    public void setmMovieID(int mMovieID) {
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

    protected Movie(Parcel in) {
        if (in.readByte() == 0) {
            mMovieID = null;
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
