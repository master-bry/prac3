package com.example.practicle3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddReview extends AppCompatActivity {

    private EditText reviewInput;
    private Button submitRevBtn;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);

        // Initialize views
        reviewInput = findViewById(R.id.review_input);
        submitRevBtn = findViewById(R.id.submit_review);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set click listener for the submit button
        submitRevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReview();
            }
        });
    }

    private void saveReview() {
        // Get the review text from the EditText
        String reviewText = reviewInput.getText().toString().trim();

        // Validate the input
        if (reviewText.isEmpty()) {
            Toast.makeText(this, "Please enter a review.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the review to the database
        long result = dbHelper.addReview(reviewText);

        // Check if the review was saved successfully
        if (result != -1) {
            Toast.makeText(this, "Review saved successfully!", Toast.LENGTH_SHORT).show();
            reviewInput.setText(""); // Clear the input field

            // Return a result to notify that a review was added
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent); // Use RESULT_OK to indicate success
            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Failed to save review.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        // Close the database connection when the activity is destroyed
        dbHelper.close();
        super.onDestroy();
    }
}