package com.dappslocker.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Tiwuya on 31,August,2018
 */
public class TrailerList {
    @SerializedName("id")
    private Integer mMovieId;
    @SerializedName("results")
    private ArrayList<Trailer> videos;

    public Integer getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(Integer mMovieId) {
        this.mMovieId = mMovieId;
    }

    public ArrayList<Trailer> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Trailer> videos) {
        this.videos = videos;
    }
}
