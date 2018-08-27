package com.dappslocker.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dappslocker.popularmovies.apikey.KeyUtil;
import com.dappslocker.popularmovies.data.MoviePreferences;
import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.model.MovieList;
import com.dappslocker.popularmovies.utilities.GetMovieDataService;
import com.dappslocker.popularmovies.utilities.NetworkUtils;
import com.dappslocker.popularmovies.utilities.RetrofitClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ImageAdapter.ImageAdapterOnClickHandler {
     private  ImageAdapter mImageAdapter;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.recycler_gridview) RecyclerView mRecylerGridView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.pb_loading_indicator)  ProgressBar mLoadingIndicator;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.error_loading_indicator)  LinearLayout mErrorLoadingImages;
    private static final String POSITION_CLICKED = "position_clicked";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_movie_main_activity);
        ButterKnife.bind(this);
        mImageAdapter = new ImageAdapter(getApplicationContext(),new ArrayList<Movie>(),this);
        mRecylerGridView.setAdapter(mImageAdapter);
        //set the preference default values
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        GridLayoutManager layoutManager
                = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false) ;
        mRecylerGridView.setLayoutManager(layoutManager);
        loadPopularMovies();
    }

    private void loadPopularMovies() {
        showPopularMovieView();
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecylerGridView.setVisibility(View.INVISIBLE);
        fetchMoviesUsingRetrofit();
    }

    private void fetchMoviesUsingRetrofit() {
        GetMovieDataService service = RetrofitClient.getRetrofitInstance().create(GetMovieDataService.class);
        Call<MovieList> call =
                service.getMovies(NetworkUtils.getEndpoint( MoviePreferences.getPrefChoice(this)), KeyUtil.getApiKey());
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if(response.isSuccessful()){
                    displayResponseData(response.body());
                }
                else{
                    displayResponseData(null);
                }
            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                displayResponseData(null);
                Toast.makeText(MainActivity.this,
                        "Error loading movies...Please try later!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayResponseData(MovieList moviesList) {
        if (moviesList != null) {
            showPopularMovieView();
            mImageAdapter.setMovieList(moviesList.getMovies());
        } else {
            displayErrorImages();
        }
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

    @Override
    public void onClick(int position) {
        Intent movieDetailIntent = new Intent(getApplicationContext(), DetailActivity.class);
        movieDetailIntent.putExtra(POSITION_CLICKED,position);
        startActivity(movieDetailIntent);
    }
}
