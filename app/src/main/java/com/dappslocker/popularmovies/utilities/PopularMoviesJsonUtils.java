package com.dappslocker.popularmovies.utilities;

import android.util.Log;

import com.dappslocker.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public final class PopularMoviesJsonUtils {
    private final static String TAG = PopularMoviesJsonUtils.class.getSimpleName();
    public static ArrayList<Movie> getSimpleWeatherStringsFromJson(String moviesJsonString) throws JSONException {

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
        JSONObject movieListJson = new JSONObject(moviesJsonString);
        if(!(movieListJson.has(PM_RESULTS))){
            return null;
        }
        //return the array of results
        JSONArray resultsArray = movieListJson.getJSONArray(PM_RESULTS);
        //iterate over the results to create a movie object
        for(int i = 0; i <resultsArray.length();i++ ){
            JSONObject movieObject= resultsArray.getJSONObject(i);
            Integer mMovieID = movieObject.getInt(PM_ID);
            String mTitle = movieObject.getString(PM_TITLE) ;
            String mPosterUrl = movieObject.getString(PM_POSTER_PATH);
            String mOverview =  movieObject.getString(PM_OVERVIEW);
            Double mRating = movieObject.getDouble(PM_POPULARITY);
            String mReleaseDate =  movieObject.getString(PM_RELEASE_DATE);

            Movie movie = new Movie(mMovieID, mTitle, mPosterUrl,mOverview, mRating,mReleaseDate );
            movieList.add(movie);
        }
        return movieList;
    }

}
