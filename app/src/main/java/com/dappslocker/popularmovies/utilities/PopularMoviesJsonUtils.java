package com.dappslocker.popularmovies.utilities;

import android.util.Log;

import com.dappslocker.popularmovies.model.Movie;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public final class PopularMoviesJsonUtils {
    private final static String TAG = PopularMoviesJsonUtils.class.getSimpleName();
    public static ArrayList<Movie> getSimpleWeatherStringsFromJson(String moviesJsonString) throws JsonParseException {
        Log.i(TAG," received movieJsonString ");
        final String PM_RESULTS = "results";
        final String PM_ID = "id"; //int
        final String PM_TITLE = "title" ;  //String
        final String PM_POSTER_PATH = "poster_path"; //String
        final String PM_OVERVIEW = "overview"; //String
        final String PM_POPULARITY = "popularity" ; //double
        final String PM_RELEASE_DATE = "release_date"; //String
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.clear();
        //parse the whole data to a single json object
        JsonElement movieListJsonElement = new JsonParser().parse(moviesJsonString);
        JsonObject  movieListJsonObject = movieListJsonElement.getAsJsonObject();
        if(!(movieListJsonObject.has(PM_RESULTS))){
            return null;
        }
        //return the array of results
        JsonArray resultsArray = movieListJsonObject.getAsJsonArray(PM_RESULTS);
        //iterate over the results to create a movie object
        for(int i = 0; i <resultsArray.size();i++ ){
            JsonObject movieObject = resultsArray.get(i).getAsJsonObject();
            Integer mMovieID = movieObject.get(PM_ID).getAsInt();
            String mTitle = movieObject.get(PM_TITLE).getAsString() ;
            String mPosterUrl = movieObject.get(PM_POSTER_PATH).getAsString();
            String mOverview =  movieObject.get(PM_OVERVIEW).getAsString();
            Double mRating = movieObject.get(PM_POPULARITY).getAsDouble();
            String mReleaseDate =  movieObject.get(PM_RELEASE_DATE).getAsString();
            Movie movie = new Movie(mMovieID, mTitle, mPosterUrl,mOverview, mRating,mReleaseDate );
            movieList.add(movie);
        }
        return movieList;
    }
}
