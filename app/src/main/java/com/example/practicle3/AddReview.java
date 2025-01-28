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
        reviewInput = findViewById(R.id.review_input);
        submitRevBtn = findViewById(R.id.submit_review);
        dbHelper = new DatabaseHelper(this);
        submitRevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReview();
            }
        });
    }

    private void saveReview() {
        String reviewText = reviewInput.getText().toString().trim();
        if (reviewText.isEmpty()) {
            Toast.makeText(this, "Please enter a review.", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.addReview(reviewText);

        if (result != -1) {
            Toast.makeText(this, "Review saved successfully!", Toast.LENGTH_SHORT).show();
            reviewInput.setText("");

            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Failed to save review.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}