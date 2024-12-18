package com.example.assignment2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;  // Import RatingBar class
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class unicefprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wwfprofile); // Ensure this layout exists

        // Get the back button using the correct Button class
        Button backButton = findViewById(R.id.backButton);  // Make sure to assign the correct ID

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will close the current activity and return to the previous one
                finish();
            }
        });

        // Get the RatingBar by its ID
        RatingBar ratingBar = findViewById(R.id.ratingBar);  // Make sure the ID is correct in the XML layout

        // Set initial properties for the RatingBar
        ratingBar.setRating(0f); // Set initial rating to 0
        ratingBar.setStepSize(0.5f); // Set step size to 1 (rating increments by 1)

        // Set the OnRatingBarChangeListener
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Show a Toast with the current rating value
                Toast.makeText(unicefprofile.this, "Rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
