package com.dappslocker.popularmovies.utilities;

import android.util.Log;

import com.dappslocker.popularmovies.model.Movie;

import com.google.gson.Gson;
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
        Gson gson = new Gson();
        for(int i = 0; i <resultsArray.size();i++ ){
            JsonObject movieObject = resultsArray.get(i).getAsJsonObject();
            Movie movie = gson.fromJson(movieObject,Movie.class);
            movieList.add(movie);
        }
        return movieList;
    }
}
