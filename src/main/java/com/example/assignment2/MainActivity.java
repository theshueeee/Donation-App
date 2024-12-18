package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button viewmorewwf;
    private Button viewmoreunicef;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Ensure this layout file exists

        // Initialize the viewmorewwf button
        viewmorewwf = findViewById(R.id.viewmorewwf);
        // Set OnClickListener for viewmorewwf
        viewmorewwf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to WWF profile
                Intent intent = new Intent(MainActivity.this, wwfprofile.class);
                startActivity(intent);
            }
        });

        // Initialize the viewmoreunicef button
        viewmoreunicef = findViewById(R.id.viewmoreunicef);
        // Set OnClickListener for viewmoreunicef
        viewmoreunicef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to UNICEF profile
                Intent intent = new Intent(MainActivity.this, unicefprofile.class);
                startActivity(intent);
            }
        });

        // Initialize the next button
        next = findViewById(R.id.next);
        // Set OnClickListener for the next button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to donation track activity
                Intent intent = new Intent(MainActivity.this, donationtrack.class);
                startActivity(intent);
            }
        });
    }
}
