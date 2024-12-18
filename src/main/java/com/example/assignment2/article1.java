package com.example.assignment2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class article1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article1);  // Ensure this layout file exists and contains a button with id 'backButton'

        // Initialize the back button
        Button backButton = findViewById(R.id.backButton);  // Make sure to assign the correct ID

        // Set the OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will close the current activity and return to the previous one
                finish();
            }
        });
    }
}
