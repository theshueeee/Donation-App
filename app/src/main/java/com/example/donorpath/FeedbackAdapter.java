package com.example.donorpath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private List<feedback> feedbackList;
    private Context context;

    public FeedbackAdapter(Context context, List<feedback> feedbackList) {
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for the feedback
        View view = LayoutInflater.from(context).inflate(R.layout.item_feeback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        feedback currentFeedback = feedbackList.get(position);

        // Bind data to the views
        holder.feedbackName.setText(currentFeedback.getNgoName()); // Set the NGO name
        holder.feedbackText.setText(currentFeedback.getFeedback()); // Set the feedback text
        holder.ratingBar.setRating(currentFeedback.getRating()); // Set the rating bar
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public void updateList(List<feedback> newList) {
        feedbackList.clear();
        feedbackList.addAll(newList);
        notifyDataSetChanged();  // Notify the adapter that the data has changed
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        private final TextView feedbackName;
        private final TextView feedbackText;
        private final RatingBar ratingBar;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views
            feedbackName = itemView.findViewById(R.id.feedbackName);
            feedbackText = itemView.findViewById(R.id.feedbackText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}




