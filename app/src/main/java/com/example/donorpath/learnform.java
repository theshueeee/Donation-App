package com.example.donorpath;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
public class learnform extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn_form, container, false);

        // Retrieve the input field and button from the layout
        EditText tipsField = view.findViewById(R.id.tips);
        Button submitButton = view.findViewById(R.id.submitFormButton);

        // Set up button click listener
        submitButton.setOnClickListener(v -> {
            // Get the input text from the EditText
            String tipsText = tipsField.getText().toString().trim();

            // Check if the input is empty
            if (tipsText.isEmpty()) {
                Toast.makeText(getContext(), "Please share your tip!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Display a thank-you toast message
            Toast.makeText(getContext(), "Thank you for submitting!", Toast.LENGTH_SHORT).show();

            // Optionally store the tip in Firestore
            saveTipToFirestore(tipsText);

            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void saveTipToFirestore(String tipsText) {
        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");

        // Prepare data to be stored
        Map<String, Object> tipData = new HashMap<>();
        tipData.put("username", username);
        tipData.put("tip", tipsText);

        // Store the tip in the "volunteerTips" collection in Firestore
        db.collection("volunteerTips").add(tipData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Your tip has been saved!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to save tip. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }
}

