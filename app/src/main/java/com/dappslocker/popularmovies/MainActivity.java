package com.dappslocker.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
        display an error messages if we are unable to bring back the movie data
        add menu item to re load the image data
        Add settings activity to enable sorting by popular or top rated
        Add detail activity

 */
    private  ImageAdapter mImageAdapter;
    private  GridView mGridview;
    private ProgressBar mLoadingIndicator;
    private LinearLayout mErrorLoadingImages;
    private static final String POSITION_CLICKED = "position_clicked";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_movie_main_activity);
        //COMPLETED create a custom adapter
        //COMPLETED load adapter with test data
        //COMPLETED Add a json parser for test data
        //COMPLETED Add Asyn Task to get list of movies
        //COMPLETED Add a progress bar to show loading process
        mImageAdapter = new ImageAdapter(getApplicationContext(),new ArrayList<Movie>());
        mGridview = (GridView) findViewById(R.id.gridview);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorLoadingImages = (LinearLayout) findViewById(R.id.error_loading_indicator);
        mGridview.setAdapter(mImageAdapter);
        //initialise the default preference
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent movieDetailIntent = new Intent(getApplicationContext(), DetailActivity.class);
                movieDetailIntent.putExtra(POSITION_CLICKED,position);
                startActivity(movieDetailIntent);
            }
        });
        //loadTestData();
        loadPopularMovies();
    }

    private void loadPopularMovies() {
        showPopularMovieView();
        String popularMpvies = MoviePreferences.getPrefChoice();
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

    private void displayErrorImages() {
        mGridview.setVisibility(View.INVISIBLE);
        mErrorLoadingImages.setVisibility(View.VISIBLE);
    }
    private void showPopularMovieView() {
        mErrorLoadingImages.setVisibility(View.INVISIBLE);
        mGridview.setVisibility(View.VISIBLE);
    }
    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.poular_movies,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menu_item_refresh :
                //reset the adapter data
                mImageAdapter.setMovieList(null);
                //reload data
                loadPopularMovies();
                return true;
            case R.id.action_settings :
                startSettingsActivity();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void startSettingsActivity() {
        //Start the settings activity
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        this.startActivity(intent);
    }

    private class FetchMoviesTask extends AsyncTask<String,Void,ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
            mGridview.setVisibility(View.INVISIBLE);
        }
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
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (moviesList != null) {
                showPopularMovieView();
                mImageAdapter.setMovieList(moviesList);
            } else {
                displayErrorImages();
            }
        }
    }

}
