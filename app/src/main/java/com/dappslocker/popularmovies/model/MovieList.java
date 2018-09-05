package com.dappslocker.popularmovies.model;
/*
 *
 * Created by Tiwuya on 22,August,2018
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This models the object that contains the list of movies
 */

public class MovieList {
    @SerializedName("page")
    private Integer mPageNum;
    @SerializedName("total_results")
    private Long mTotalResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("results")
    private ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
