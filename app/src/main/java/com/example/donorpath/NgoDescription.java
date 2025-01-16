package com.example.donorpath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NgoDescription extends Fragment {

    private ImageView ngoLogo;
    private TextView ngoName, ngoAbout;
    private Button donateNowButton, submitFeedbackButton, backButton;
    private RatingBar ratingBar;
    private EditText feedbackEditText, feedbackEditName;
    private LinearLayout feedbackSection;

    private ArrayList<Ngo> ngoList;
    private String currentNgoUrl;  // Store the NGO URL for later use

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ngo_description, container, false);

        // Initialize views
        initializeViews(view);

        // Initially hide feedback section
        feedbackSection.setVisibility(View.GONE);

        // Get the NGO data passed from the previous fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            setupNgoData(bundle);
        }

        return view;
    }

    private void initializeViews(View view) {
        ngoLogo = view.findViewById(R.id.ngoLogo);
        ngoName = view.findViewById(R.id.ngoName);
        ngoAbout = view.findViewById(R.id.ngoAbout);
        donateNowButton = view.findViewById(R.id.buttonDonateNow);
        submitFeedbackButton = view.findViewById(R.id.submitFeedbackButton);
        ratingBar = view.findViewById(R.id.ratingBar);
        feedbackEditText = view.findViewById(R.id.feedbackEditText);
        feedbackEditName = view.findViewById(R.id.feedbackEditName);
        feedbackSection = view.findViewById(R.id.feedbackSection);
        backButton = view.findViewById(R.id.backButton);
    }

    private void setupNgoData(Bundle bundle) {
        String ngoNameStr = bundle.getString("name");
        String ngoAboutStr = bundle.getString("about");
        int logoResId = bundle.getInt("logoResId");
        currentNgoUrl = bundle.getString("ngoUrl");

        // Set data to UI elements
        ngoName.setText(ngoNameStr);
        ngoAbout.setText(ngoAboutStr);
        ngoLogo.setImageResource(logoResId);

        setupDonateButton(ngoNameStr, ngoAboutStr, logoResId);
        setupSubmitFeedbackButton();
        setupBackButton();
    }

    private void setupDonateButton(final String ngoNameStr, final String ngoAboutStr, final int logoResId) {
        donateNowButton.setOnClickListener(v -> {
            // First open the browser with the NGO's URL
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNgoUrl));
            startActivity(browserIntent);

            // Show the feedback section after the browser is launched
            feedbackSection.setVisibility(View.VISIBLE);
        });
    }

    private void setupSubmitFeedbackButton() {
        submitFeedbackButton.setOnClickListener(view -> {
            String feedbackText = feedbackEditText.getText().toString().trim();
            float ratingValue = ratingBar.getRating();
            String ngoName = feedbackEditName.getText().toString().trim();  // Get the NGO name from the input

            if (feedbackText.isEmpty()) {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "Please provide your feedback", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // Retrieve the username from SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);

            if (username == null) {
                // Handle case where username is not found, maybe show a message or redirect to login
                Toast.makeText(getActivity(), "User not logged in. Please log in first.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new feedback object
            Map<String, Object> feedbackData = new HashMap<>();
            feedbackData.put("username", username);
            feedbackData.put("ngoName", ngoName);
            feedbackData.put("rating", ratingValue);
            feedbackData.put("feedback", feedbackText);

            // Store the feedback in Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("feedbacks").add(feedbackData)
                    .addOnSuccessListener(documentReference -> {
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "Feedback submitted!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "Error submitting feedback", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }


    private void setupBackButton() {
        backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}







