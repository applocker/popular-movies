package com.dappslocker.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Tiwuya on 01,September,2018
 */
public class UserReviewList {
    @SerializedName("id")
    private Integer mMovieId;
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private ArrayList<UserReview> userReviews;

    public Integer getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(Integer mMovieId) {
        this.mMovieId = mMovieId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ArrayList<UserReview> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(ArrayList<UserReview> userReviews) {
        this.userReviews = userReviews;
    }
}
