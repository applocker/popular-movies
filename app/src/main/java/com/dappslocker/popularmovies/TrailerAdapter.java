package com.dappslocker.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dappslocker.popularmovies.model.Trailer;

import java.util.List;

/**
 * Created by Tiwuya on 29,August,2018
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private final static  String TAG = TrailerAdapter.class.getSimpleName();
    private static List<Trailer> trailers;
    private final TrailerAdapterOnClickHandler mClickHandler;

    public  void setTrailers(List<Trailer> trailers) {
        TrailerAdapter.trailers = null;
        TrailerAdapter.trailers = trailers;
        notifyDataSetChanged();
    }

    public interface TrailerAdapterOnClickHandler {
        void onClickTrailer(int position);
    }

    TrailerAdapter(List<Trailer> trailers,TrailerAdapterOnClickHandler clickHandler ) {
        TrailerAdapter.trailers = trailers;
        mClickHandler = clickHandler;
    }
    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_trailer,parent,false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
        holder.textViewMovieTitle.setText(trailers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (trailers == null)
            return 0;
        return trailers.size();
    }

    public static Trailer getTrailerAtPosition(int position) {
        if (trailers == null){
            return null;
        }
        else
            return trailers.get(position);
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView textViewMovieTitle;
        final ImageView imageViewPLayMovie;

        TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            textViewMovieTitle = itemView.findViewById(R.id.textView_trailer_movie_name);
            imageViewPLayMovie = itemView.findViewById(R.id.imageView_play_trailer);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClickTrailer(adapterPosition);
        }
    }
}
