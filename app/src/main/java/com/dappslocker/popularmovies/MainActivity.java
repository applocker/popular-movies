package com.dappslocker.popularmovies;

import android.net.Network;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.dappslocker.popularmovies.data.MoviePreferences;
import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.utilities.NetworkUtils;
import com.dappslocker.popularmovies.utilities.PopularMoviesJsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
/*
    Task
        Upon launch, present the user with an grid arrangement of movie posters.
        Allow your user to change sort order via a setting:
        The sort order can be by most popular, or by top rated
        Allow the user to tap on a movie poster and transition to a details screen with additional information
        show a progress bar while loading the images

 */
    private  ImageAdapter mImageAdapter;
    private  GridView mGridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //COMPLETED create a custom adapter
        //COMPLETED load adapter with test data
        //COMPLETED Add a json parser for test data
        //COMPLETED Add Asyn Task to get list of movies
        //TODO Add a progress bar to show loading process
        mImageAdapter = new ImageAdapter(this,new ArrayList<Movie>());
        mGridview = (GridView) findViewById(R.id.gridview);
        mGridview.setAdapter(mImageAdapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        //loadTestData();
        loadPopularMovies();
    }

    private void loadPopularMovies() {
        String popularMpvies = MoviePreferences.getDefaultPrefChoice();
        new FetchMoviesTask().execute(popularMpvies);
    }

    private void loadTestData() {
        ArrayList<Movie> listOfMovies = new ArrayList<>();
        for(int i = 0 ;i<100; i++){
            Movie  movie = new Movie(1,"Interstellar","","",1.0,"");
            listOfMovies.add(movie);
        }
        mImageAdapter.setMovieList(listOfMovies);
    }

    private class FetchMoviesTask extends AsyncTask<String,Void,ArrayList<Movie>> {
        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            if (params.length == 0){
                return null;
            }
           //get the first parameter
            String moviePref = params[0];
            //build url using the pref
            URL url = NetworkUtils.buildUrl(moviePref);
            ArrayList<Movie> movieList = null;
            try{
                //get json response
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(url);
                movieList = PopularMoviesJsonUtils.getSimpleWeatherStringsFromJson(jsonMovieResponse);
                return movieList;
            }catch(Exception ex){
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> moviesList) {
            //TODO Show loading Indicator
            if (moviesList != null) {
                mImageAdapter.setMovieList(moviesList);
            } else {
                //TODO DisplayErrorImages();
            }
        }
    }
}
