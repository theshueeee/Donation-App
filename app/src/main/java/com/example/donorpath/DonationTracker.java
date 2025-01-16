package com.example.donorpath;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
public class DonationTracker extends Fragment {

    private RecyclerView feedbackRecyclerView;
    private TextView donationCountText;
    private List<feedback> feedbackList;
    private FeedbackAdapter feedbackAdapter;
    private List<feedback> filteredFeedbackList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_donation_tracker, container, false);

        EditText searchBar = rootView.findViewById(R.id.search_bar);
        feedbackRecyclerView = rootView.findViewById(R.id.feedbackRecyclerView);
        donationCountText = rootView.findViewById(R.id.donationCountText);

        feedbackList = new ArrayList<>();
        filteredFeedbackList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(getContext(), filteredFeedbackList);

        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        feedbackRecyclerView.setAdapter(feedbackAdapter);

        // Fetch all feedbacks and user's specific feedback count
        fetchAllFeedbacks();

        // Add TextWatcher to the search bar to filter feedbacks
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
                filterFeedbacks(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return rootView;
    }

    private void fetchAllFeedbacks() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference feedbackRef = db.collection("feedbacks");

        // Get all feedbacks (for display)
        feedbackRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<feedback> feedbackListFromDb = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            feedback currentFeedback = document.toObject(feedback.class);
                            if (currentFeedback != null) {
                                feedbackListFromDb.add(currentFeedback);
                            }
                        }

                        // Set the feedback list to the adapter
                        feedbackList.clear();
                        feedbackList.addAll(feedbackListFromDb);
                        filteredFeedbackList.clear();
                        filteredFeedbackList.addAll(feedbackList); // Initially, show all feedbacks
                        feedbackAdapter.notifyDataSetChanged();

                        // Now fetch the count of feedbacks submitted by the current user
                        fetchUserFeedbackCount();
                    } else {
                        Toast.makeText(getContext(), "No feedbacks found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error fetching feedbacks", Toast.LENGTH_SHORT).show();
                });
    }

    private void filterFeedbacks(String query) {
        // Clear the filtered list
        filteredFeedbackList.clear();

        // Filter the feedbacks by NGO name
        for (feedback fb : feedbackList) {
            if (fb.getNgoName() != null && fb.getNgoName().toLowerCase().contains(query.toLowerCase())) {
                filteredFeedbackList.add(fb);
            }
        }

        // Notify the adapter about the changes
        feedbackAdapter.notifyDataSetChanged();


    }

    private void fetchUserFeedbackCount() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        if (username != null) {
            // Query Firestore to fetch feedbacks for the specific user by username
            db.collection("feedbacks")
                    .whereEqualTo("username", username) // Use the username to filter feedbacks
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        int feedbackCount = queryDocumentSnapshots.size();

                        // Update the donation count with the user's feedback count only
                        donationCountText.setText("Total Impact: " + feedbackCount + " feedbacks");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Error fetching user feedback count", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Username not found", Toast.LENGTH_SHORT).show();
        }
    }
}




