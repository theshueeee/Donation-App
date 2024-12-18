package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class donationtrack extends AppCompatActivity {

    private Button back;  // Declare the Button variable
    private Button next;
    private Button viewmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donationtrack); // Ensure the correct layout file is used

        // Initialize the back button
        back = findViewById(R.id.back);  // Corrected Button initialization

        // Set OnClickListener for the back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the current activity and return to the previous one
                finish();
            }
        });

        // Initialize the viewmore button
        viewmore = findViewById(R.id.viewmore);

        // Set OnClickListener to the viewmore button
        viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to the wwfprofile activity
                Intent intent = new Intent(donationtrack.this, wwfprofile.class);
                startActivity(intent);
            }
        });

        // Initialize the next button
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donationtrack.this, volunteeroppor.class);
                startActivity(intent);
            }
        });
    }
}
