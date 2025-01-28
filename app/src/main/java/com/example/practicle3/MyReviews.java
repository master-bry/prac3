package com.example.practicle3;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MyReviews extends Fragment {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<Review> reviewList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_reviews, container, false);

        Log.d("MyReviews", "Fragment created and view inflated.");

        recyclerView = view.findViewById(R.id.reviews_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dbHelper = new DatabaseHelper(getActivity());
        reviewList = new ArrayList<>();
        fetchReviews();

        adapter = new ReviewAdapter(getActivity(), reviewList, dbHelper);
        recyclerView.setAdapter(adapter);
        Log.d("MyReviews", "Adapter set up successfully.");
        return view;
    }

    private void fetchReviews() {
        Log.d("MyReviews", "Fetching reviews from the database.");
        reviewList.clear();

        Cursor cursor = dbHelper.getAllReviews();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") Review review = new Review(
                            cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REVIEW_TEXT)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIMESTAMP))
                    );
                    reviewList.add(review);
                    Log.d("MyReviews", "Review added: " + review.toString());
                } while (cursor.moveToNext());
            } else {
                Log.d("MyReviews", "No reviews found in the database.");
            }
            cursor.close();
        } else {
            Log.e("MyReviews", "Cursor is null. Check the database query.");
        }

        // Notify the adapter of data changes
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            Log.d("MyReviews", "Adapter notified of data changes.");
        } else {
            Log.e("MyReviews", "Adapter is null. Check initialization.");
        }
    }
}