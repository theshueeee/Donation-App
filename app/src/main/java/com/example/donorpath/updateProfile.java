package com.example.donorpath;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class updateProfile extends Fragment {
    private EditText editName, editEmail, editPassword;
    private Button saveButton;
    private FirebaseFirestore firestore;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Retrieve the user ID from SharedPreferences (or arguments if passed directly)
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        if (getArguments() != null) {
            String name = getArguments().getString("name", "");
            String email = getArguments().getString("email", "");
            String password = getArguments().getString("password", "");

            // Populate the EditText fields with the received data
            editName = view.findViewById(R.id.editname);
            editEmail = view.findViewById(R.id.editEmail);
            editPassword = view.findViewById(R.id.editPassword);

            editName.setText(name);
            editEmail.setText(email);
            editPassword.setText(password);
        }

        // Initialize views
        saveButton = view.findViewById(R.id.saveButton);

        // Load current user data from Firestore
        loadUserData();

        // Set click listener for the Save button
        saveButton.setOnClickListener(v -> saveUserData());

        return view;
    }

    private void loadUserData() {
        if (username.isEmpty()) {
            Toast.makeText(getContext(), "Username  is missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        firestore.collection("users").document(username).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Populate the fields with user data
                        String name = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");
                        String password = documentSnapshot.getString("password");

                        editName.setText(name);
                        editEmail.setText(email);
                        editPassword.setText(password);
                    } else {
                        Toast.makeText(getContext(), "User not found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to load user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveUserData() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to update the Firestore document
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("name", name);
        userUpdates.put("email", email);
        userUpdates.put("password", password);

        firestore.collection("users").document(username).update(userUpdates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
