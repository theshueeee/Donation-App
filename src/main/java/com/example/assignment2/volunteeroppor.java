package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class volunteeroppor extends AppCompatActivity {

    private Button back;  // Declare the Button variable
    private Button viewmorewwf;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteeroppor); // Make sure you have the correct layout

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

        viewmorewwf = findViewById(R.id.viewmorewwf);
        viewmorewwf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(volunteeroppor.this, wwfprofile.class);
                startActivity(intent);
            }
        });

        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(volunteeroppor.this, awarenesspage1.class);
                startActivity(intent);
            }
        });

    }
}
