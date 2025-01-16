package com.example.donorpath;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class editProfile extends AppCompatActivity {
    private EditText editName, editEmail, editPassword;
    private Button saveButton;
    private FirebaseFirestore db;
    private FirebaseUser currentUser; // To get the current logged-in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firestore and the current user
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser(); // Get the current logged-in user

        // Initialize EditText fields
        editName = findViewById(R.id.editname);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        // Initialize save button
        saveButton = findViewById(R.id.saveButton);

        // Retrieve data passed from ProfilePageFragment
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        // Set current values in the EditText fields
        editName.setText(name);
        editEmail.setText(email);
        editPassword.setText(password);

        // Handle save button click
        saveButton.setOnClickListener(v -> {
            // Get updated values from EditText fields
            String updatedName = editName.getText().toString();
            String updatedEmail = editEmail.getText().toString();
            String updatedPassword = editPassword.getText().toString();

            // Update Firestore document with the new data
            if (currentUser != null) {
                // Create a map with the updated profile data
                Map<String, Object> updatedData = new HashMap<>();
                updatedData.put("name", updatedName);
                updatedData.put("email", updatedEmail);
                updatedData.put("password", updatedPassword);

                // Update the user's profile in Firestore
                db.collection("users")
                        .document(currentUser.getUid()) // Use the current user's UID to update the document
                        .update(updatedData)
                        .addOnSuccessListener(aVoid -> {
                            // Successfully updated Firestore
                            Toast.makeText(editProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                            // Pass the updated data back to ProfilePageFragment
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("name", updatedName);
                            resultIntent.putExtra("email", updatedEmail);
                            resultIntent.putExtra("password", updatedPassword);
                            setResult(RESULT_OK, resultIntent);
                            finish(); // Close the activity and return to ProfilePageFragment
                        })
                        .addOnFailureListener(e -> {
                            // Handle errors (e.g., failed to update Firestore)
                            Toast.makeText(editProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


