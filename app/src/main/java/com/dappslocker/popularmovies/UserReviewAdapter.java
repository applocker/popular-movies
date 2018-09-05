package com.dappslocker.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dappslocker.popularmovies.model.UserReview;

import java.util.List;

/**
 * Created by Tiwuya on 30,August,2018
 */
public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.UserReviewAdapterViewHolder> {
    private static List<UserReview> userReviews;

    public  void setUserReviews(List<UserReview> userReviews) {
        UserReviewAdapter.userReviews = null;
        UserReviewAdapter.userReviews = userReviews;
        notifyDataSetChanged();
    }

    UserReviewAdapter(List<UserReview> userReviews ) {
        UserReviewAdapter.userReviews = userReviews;
    }

    @NonNull
    @Override
    public UserReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_user_review,parent,false);
        return new UserReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviewAdapterViewHolder holder, int position) {
        holder.textViewAuthor.setText(userReviews.get(position).getAuthor());
        holder.textViewContent.setText(userReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (userReviews == null)
            return 0;
        return userReviews.size();
    }

    class UserReviewAdapterViewHolder extends RecyclerView.ViewHolder{
        final TextView textViewAuthor;
        final TextView textViewContent;

        UserReviewAdapterViewHolder(View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textView_author);
            textViewContent = itemView.findViewById(R.id.textView_content);
        }
    }
}

