package com.example.practicle3;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout toolbar;
    LinearLayout home, reviews, favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout first

        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar); // Initialize toolbar after setContentView
        menu = toolbar.findViewById(R.id.menu); // Find menu button within the toolbar
        home = findViewById(R.id.home);
        reviews = findViewById(R.id.reviews);
        favorites = findViewById(R.id.myfavorite);

        // Check for null views
        if (menu == null || home == null || reviews == null || favorites == null) {
            throw new RuntimeException("One or more views are null. Check IDs in XML layout.");
        }

        // Set onClickListeners
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new Home()); // Replace with HomeFragment
                closeDrawer(drawerLayout); // Close the drawer after selection
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MyReviews()); // Replace with MyReviewsFragment
                closeDrawer(drawerLayout); // Close the drawer after selection
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new Favorites()); // Replace with FavoritesFragment
                closeDrawer(drawerLayout); // Close the drawer after selection
            }
        });

        // Load the default fragment (e.g., HomeFragment) when the activity starts
        if (savedInstanceState == null) {
            replaceFragment(new Home());
        }
    }

    // Method to replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment); // Use correct container ID
        fragmentTransaction.commit();
    }

    // Method to open the drawer
    public static void openDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // Method to close the drawer
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout); // Close the drawer when the activity is paused
    }
}