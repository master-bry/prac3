package com.example.practicle3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private List<Review> reviewList;
    private DatabaseHelper dbHelper;

    public ReviewAdapter(Context context, List<Review> reviewList, DatabaseHelper dbHelper) {
        this.context = context;
        this.reviewList = reviewList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.reviewText.setText(review.getReviewText());
        holder.reviewTimestamp.setText(review.getTimestamp());

        // Edit Button Click Listener
        holder.editButton.setOnClickListener(v -> {
            // Implement edit functionality
            Toast.makeText(context, "Edit Review: " + review.getId(), Toast.LENGTH_SHORT).show();
        });

        // Delete Button Click Listener
        holder.deleteButton.setOnClickListener(v -> {
            // Delete the review from the database
            dbHelper.deleteReview(review.getId());
            reviewList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, reviewList.size());
            Toast.makeText(context, "Review deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewText, reviewTimestamp;
        Button editButton, deleteButton;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewText = itemView.findViewById(R.id.review_text);
            reviewTimestamp = itemView.findViewById(R.id.review_timestamp);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}