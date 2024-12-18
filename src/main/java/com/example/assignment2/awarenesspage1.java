package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class awarenesspage1 extends AppCompatActivity {

    private Button btnarticle1;
    private Button back;

    @Override  // Adding @Override for clarity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awarenesspage1);

        // Initialize the btnarticle1 button
        btnarticle1 = findViewById(R.id.btnarticle1);

        // Set OnClickListener for btnarticle1 button to navigate to article1 activity
        btnarticle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(awarenesspage1.this, article1.class);
                startActivity(intent);
            }
        });

        // Initialize the back button
        back = findViewById(R.id.back);

        // Set OnClickListener for the back button to finish the current activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Close the current activity and return to the previous one
            }
        });
    }
}
