package com.dappslocker.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dappslocker.popularmovies.database.FavouriteMoviesDao;
import com.dappslocker.popularmovies.database.MoviesDatabase;
import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String POSITION_CLICKED = "position_clicked";
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_movie_id)  TextView mTextViewMovieId;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_title)  TextView mTextViewTitle;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_rating)  TextView mTextViewRating;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_release_date)  TextView mTextViewReleaseDate;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_overview)  TextView mTextViewOverview;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.imageView_movie_poster)   ImageView mImageViewMoviePoster;
    @BindView(R.id.toggleButton_favourite)   ToggleButton mToggleButtonFavourite;

    private MoviesDatabase mMoviesDatabase;
    private static final String TAG = DetailActivity.class.getSimpleName();
    private int mSelectedMoviePosition;
    private Movie mCurrentSelectedMovie;
    private List<Movie> mfavouriteMovies = null;
    private static final int MOVIE_POSITION_FOR_QUERY_OPERATION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_movie_detail_activity);
        ButterKnife.bind(this);
        //create the database instance
        mMoviesDatabase = MoviesDatabase.getInstance(this);
        Intent intent = getIntent();
        if(intent.hasExtra(POSITION_CLICKED)){
            mSelectedMoviePosition = intent.getIntExtra(POSITION_CLICKED,0);
            displayDataForItemAtPosition(mSelectedMoviePosition);
            setFavouriteMovieToggleButtonState();
        }
    }

    private void setFavouriteMovieToggleButtonState() {
        mToggleButtonFavourite.setChecked(false);
        executeDbOps(mSelectedMoviePosition,DatabaseOperationType.QUERY_SINGLE);
    }



    private  void  loadAllFavouriteMovies() {
        executeDbOps(MOVIE_POSITION_FOR_QUERY_OPERATION,DatabaseOperationType.QUERY);
    }

    private void executeDbOps(int position, final DatabaseOperationType dbOperation) {
        final Movie movie = position == MOVIE_POSITION_FOR_QUERY_OPERATION ? null:ImageAdapter.getMovieAtPosition(position);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final FavouriteMoviesDao favouriteMoviesDao = mMoviesDatabase.favouriteMoviesDao();
                if (dbOperation == DatabaseOperationType.INSERT && movie!= null) {
                    final Movie sMovie = favouriteMoviesDao.loadTaskById(movie.getMovieID());
                    if(sMovie == null){
                        favouriteMoviesDao.insertMovie(movie);
                        Log.i(TAG," executeDbOps: insert movie to the database");
                    }
                    showAllFavouriteMovies(favouriteMoviesDao);
                }else if ( dbOperation == DatabaseOperationType.DELETE && movie!= null){
                   favouriteMoviesDao.deleteMovie(movie);
                    Log.i(TAG," executeDbOps: deleted movie-> " + movie.getTitle());
                    showAllFavouriteMovies(favouriteMoviesDao);
                }else if ( dbOperation == DatabaseOperationType.QUERY_SINGLE && movie!= null){
                    final Movie sMovie = favouriteMoviesDao.loadTaskById(movie.getMovieID());
                    if(sMovie != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mToggleButtonFavourite.setChecked(true);
                                Log.i(TAG," executeDbOps: loaded all movies ");
                            }
                        });
                    }
                    showAllFavouriteMovies(favouriteMoviesDao);
                }else if ( dbOperation == DatabaseOperationType.QUERY){
                    final List<Movie> movies = favouriteMoviesDao.loadAllMovies();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mfavouriteMovies = movies;
                            Log.i(TAG," executeDbOps: loaded all movies ");
                        }
                    });
                    showAllFavouriteMovies(favouriteMoviesDao);
                }
            }
            private void showAllFavouriteMovies(FavouriteMoviesDao favouriteMoviesDao) {
                final List<Movie> movies = favouriteMoviesDao.loadAllMovies();
                if(movies != null && movies.size() > 0 ){
                    for (Movie m: movies) {
                        Log.i(TAG," executeDbOps:showAllFavouriteMovies " + m.getMovieID()
                                + " " +  m.getTitle());
                    }
                }
            }

            });
    }

    private void saveFavouriteToDatabase(int position, final DatabaseOperationType dbOperation) {
        executeDbOps(position,dbOperation);
    }

    private void displayDataForItemAtPosition(int position) {
        mCurrentSelectedMovie= ImageAdapter.getMovieAtPosition(position);
        if(mCurrentSelectedMovie != null ){
            mTextViewMovieId.setText( String.format(getResources().getConfiguration().locale,"%d", mCurrentSelectedMovie.getMovieID()));
            mTextViewTitle.setText(mCurrentSelectedMovie.getTitle());
            mTextViewRating.setText(String.format(getResources().getConfiguration().locale,"%f", mCurrentSelectedMovie.getRating()));
            mTextViewReleaseDate.setText(mCurrentSelectedMovie.getReleaseDate());
            mTextViewOverview.setText(mCurrentSelectedMovie.getOverview());
            loadImage(mCurrentSelectedMovie);
        }
    }

    private void loadImage(Movie movie) {
        //re-load the image with picasso here using the poster url the images should now be cached in memory
        String imgUrl = NetworkUtils.getPopularMoviesImagesUrlBase() + movie.getPosterUrl();
        Picasso.with(getApplicationContext()).load(imgUrl)
                .placeholder(R.drawable.ic_image_black_48dp)
                .into(mImageViewMoviePoster);
    }

    public void favouriteMovieClicked(View view) {
        if (mToggleButtonFavourite.isChecked()) {
            saveFavouriteToDatabase(mSelectedMoviePosition, DatabaseOperationType.INSERT);
            Log.i(TAG," favouriteMovieClicked: isChecked() -> true ");
        } else {
            //Delete movies from the database
            executeDbOps(mSelectedMoviePosition,DatabaseOperationType.DELETE);
            Log.i(TAG," favouriteMovieClicked: isChecked() -> false ");
        }
    }

    public enum DatabaseOperationType {
        INSERT,
        DELETE,
        QUERY,
        QUERY_SINGLE
    }

}
