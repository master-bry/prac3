package com.example.practicle3;

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

public class Favorites extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<Favorite> favoriteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.favorites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(getActivity());

        // Fetch favorites from the database
        favoriteList = new ArrayList<>();
        fetchFavorites();

        // Set up the adapter
        adapter = new FavoriteAdapter(getActivity(), favoriteList, dbHelper);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void fetchFavorites() {
        // Clear the existing list
        favoriteList.clear();

        // Fetch favorites from the database
        Cursor cursor = dbHelper.getAllFavorites();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Favorite favorite = new Favorite(
                            cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FAVORITE_TEXT)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIMESTAMP))
                    );
                    favoriteList.add(favorite);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        // Notify the adapter of data changes
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}