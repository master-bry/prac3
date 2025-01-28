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
        favoriteInput = findViewById(R.id.favorite_input);
        submitFavBtn = findViewById(R.id.submit_favorite);

        dbHelper = new DatabaseHelper(this);
        submitFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavorite(); // Call the correct method
            }
        });
    }

    private void saveFavorite() {
        String favoriteText = favoriteInput.getText().toString().trim();
        if (favoriteText.isEmpty()) {
            Toast.makeText(this, "Please enter a favorite restaurant.", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.addFavorite(favoriteText);

        if (result != -1) {
            Toast.makeText(this, "Favorite saved successfully!", Toast.LENGTH_SHORT).show();
            favoriteInput.setText("");

            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Failed to save favorite.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}