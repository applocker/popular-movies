package com.dappslocker.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.dappslocker.popularmovies.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Tiwuya on 09/07/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String POSITION_CLICKED = "position_clicked";
    @BindView(R.id.textView_movie_id)  TextView mTextViewMovieId;
    @BindView(R.id.textView_title)  TextView mTextViewTitle;
    @BindView(R.id.textView_rating)  TextView mTextViewRating;
    @BindView(R.id.textView_release_date)  TextView mTextViewReleaseDate;
    @BindView(R.id.textView_overview)  TextView mTextViewOverview;
    @BindView(R.id.imageView_movie_poster)   ImageView mImageViewMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_movie_detail_activity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if(intent.hasExtra(POSITION_CLICKED)){
            int position = intent.getIntExtra(POSITION_CLICKED,0);
            dispalyDataForItemAtPosition(position);
        }
    }

    private void dispalyDataForItemAtPosition(int position) {
        Movie movie = ImageAdapter.getMovieAtPosition(position);
        if(movie != null ){
            mTextViewMovieId.setText(Integer.valueOf(movie.getmMovieID()).toString());
            mTextViewTitle.setText(movie.getTitle());
            mTextViewRating.setText(Double.valueOf(movie.getRating()).toString());
            mTextViewReleaseDate.setText(movie.getReleaseDate());
            mTextViewOverview.setText(movie.getOverview());
            mImageViewMoviePoster.setImageDrawable(movie.getPosterImage());
            mImageViewMoviePoster.invalidate();
        }
    }
}
