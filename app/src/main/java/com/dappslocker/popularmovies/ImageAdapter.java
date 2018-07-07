package com.dappslocker.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Tiwuya on 05/07/2018.
 */
public class ImageAdapter extends ArrayAdapter<Movie> {
    private Context mContext;
    private List<Movie> movieList;

    public ImageAdapter(Context c,List<Movie> movieList) {
        super(c,0,movieList);
        mContext = c;
        this.movieList = movieList;
    }

    public int getCount() {
        return movieList.size();
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    public long getItemId(int position) {
        return movieList.indexOf( movieList.get(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, parent, false);
            imageView = (ImageView) convertView.findViewById(R.id.imageView_grid_item);
        }else  {
            imageView = (ImageView) convertView;
        }
        //load the image with picasso here using the poster url
        String imgUrl = NetworkUtils.getPopularMoviesImagesUrlBase() + movieList.get(position).getPosterUrl();
        Picasso.with(mContext)
                .load(imgUrl)
                .placeholder(R.drawable.ic_image_black_48dp)
                .into(imageView);
        return imageView;
    }


    public void setMovieList(List<Movie> movieList) {
        this.movieList = null;
        this.movieList = movieList;
        notifyDataSetChanged();
    }
}
