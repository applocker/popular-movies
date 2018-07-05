package com.dappslocker.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.dappslocker.popularmovies.model.Movie;

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
        //TODO create a custom adapter
        //TODO load adapter with test data
        //TODO Add a json parser for test data
        //TODO Add Asyn Task to get list of movies
        //TODO Add a progress bar to show loading process
        mImageAdapter = new ImageAdapter(this,new ArrayList<Movie>());
        mGridview = (GridView) findViewById(R.id.gridview);
        mGridview.setAdapter(mImageAdapter);
        loadTestData();
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTestData() {
        ArrayList<Movie> listOfMovies = new ArrayList<>();
        for(int i = 0 ;i<100; i++){
            Movie  movie = new Movie(1,"Interstellar","","",1.0,"");
            listOfMovies.add(movie);
        }
        mImageAdapter.setMovieList(listOfMovies);
    }


}
