package com.dappslocker.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dappslocker.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ImageAdapter.ImageAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.recycler_gridview) RecyclerView mRecylerGridView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.pb_loading_indicator)  ProgressBar mLoadingIndicator;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.error_loading_indicator)  LinearLayout mErrorLoadingImages;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_error_loading_message) TextView mErrorLoadingMessage;

    private  ImageAdapter mImageAdapter;
    private static final String POSITION_CLICKED = "position_clicked";
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_movie_main_activity);
        ButterKnife.bind(this);
        mImageAdapter = new ImageAdapter(getApplicationContext(),new ArrayList<Movie>(),this);
        mRecylerGridView.setAdapter(mImageAdapter);
        //set the preference default values
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        int orientation = this.getResources().getConfiguration().orientation;
        GridLayoutManager layoutManager;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false) ;
        } else {
            layoutManager = new GridLayoutManager(this,5,GridLayoutManager.VERTICAL,false) ;
        }
        mRecylerGridView.setLayoutManager(layoutManager);
        setupViewModel();
    }

    private void startMoviesLoadingIndicator() {
        showPopularMovieView();
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecylerGridView.setVisibility(View.INVISIBLE);
    }

    private void setupViewModel() {
        startMoviesLoadingIndicator();
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesList) {
                Log.d(TAG, "Updating movie list from LiveData in ViewModel");
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if(moviesList!= null){
                   showPopularMovieView();
                }
                else{
                    displayErrorImages();
                }
                mImageAdapter.setMovieList(moviesList);
            }
        });
    }

    private void loadTestData() {
        ArrayList<Movie> listOfMovies = new ArrayList<>();
        for(int i = 0 ;i<100; i++){
            Movie  movie = new Movie(1,"Interstellar","","",1.0f,"");
            listOfMovies.add(movie);
        }
        mImageAdapter.setMovieList(listOfMovies);
    }

    private void displayErrorImages() {
        String pref = MoviePreferences.getPrefChoice(this);
        if(pref.equals(MoviePreferences.getPrefFavourite())){
            mErrorLoadingMessage.setText(getResources().getString(R.string.error_message_no_favourite));
        }
        else{
            mErrorLoadingMessage.setText(getResources().getString(R.string.error_message));
        }
        mRecylerGridView.setVisibility(View.INVISIBLE);
        mErrorLoadingImages.setVisibility(View.VISIBLE);
    }

    private void showPopularMovieView() {
        mErrorLoadingImages.setVisibility(View.INVISIBLE);
        mRecylerGridView.setVisibility(View.VISIBLE);
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
                startMoviesLoadingIndicator();
                MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
                viewModel.refreshData();
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

    @Override
    public void onClick(int position) {
        Intent movieDetailIntent = new Intent(getApplicationContext(), DetailActivity.class);
        movieDetailIntent.putExtra(POSITION_CLICKED,position);
        startActivity(movieDetailIntent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        MainViewModel.setPrefChanged(true);
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.refreshDataIfPrefChanged();
    }

}
