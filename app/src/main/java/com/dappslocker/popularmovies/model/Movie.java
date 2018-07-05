package com.dappslocker.popularmovies.model;

import java.util.Date;

/**
 * Created by Tiwuya on 03/07/2018.
 */

public class Movie {
    private Integer mMovieID;
    private String mTitle;
    private String mPosterUrl;
    private String mOverview;
    private Double mRating;
    private String mReleaseDate;

    public Movie(int mMovieID, String mTitle, String mPosterUrl,String mOverview, Double mRating,String mReleaseDate ){
        this.mMovieID = mMovieID;
        this.mTitle = mTitle;
        this.mPosterUrl = mPosterUrl;
        this.mOverview = mOverview;
        this.mRating = mRating;
        this.mReleaseDate = mReleaseDate;
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

    public String getOvervie() {
        return mOverview;
    }

    public void setOvervie(String mOvervie) {
        this.mOverview = mOvervie;
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


}
