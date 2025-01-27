package com.example.practicle3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddFavorite extends AppCompatActivity {

    private EditText favoriteInput;
    private Button submitFavBtn;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_favorite);

        // Initialize views
        favoriteInput = findViewById(R.id.favorite_input);
        submitFavBtn = findViewById(R.id.submit_favorite);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set click listener for the submit button
        submitFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavorite(); // Call the correct method
            }
        });
    }

    private void saveFavorite() {
        // Get the favorite text from the EditText
        String favoriteText = favoriteInput.getText().toString().trim();

        // Validate the input
        if (favoriteText.isEmpty()) {
            Toast.makeText(this, "Please enter a favorite restaurant.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the favorite to the database
        long result = dbHelper.addFavorite(favoriteText);

        // Check if the favorite was saved successfully
        if (result != -1) {
            Toast.makeText(this, "Favorite saved successfully!", Toast.LENGTH_SHORT).show();
            favoriteInput.setText(""); // Clear the input field

            // Return a result to notify that a favorite was added
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent); // Use RESULT_OK to indicate success
            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Failed to save favorite.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        // Close the database connection when the activity is destroyed
        dbHelper.close();
        super.onDestroy();
    }
}