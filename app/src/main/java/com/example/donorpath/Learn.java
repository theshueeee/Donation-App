package com.example.donorpath;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
public class Learn extends Fragment {

    private RecyclerView recyclerView;
    private learnAdapter adapter;
    private List<LearnClass> learnList;

    private List<String> tipsList;  // Use String type for tips

    private TipsAdapter tipsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView tipsRecyclerView = view.findViewById(R.id.tipsRecyclerView);
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Initialize the list of LearnClass items
        learnList = new ArrayList<>();
        learnList.add(new LearnClass("Understanding Global Poverty Better", "5 min . Article", R.drawable.pic4, "Read now", "https://www.technoserve.org/blog/what-is-poverty-understanding-the-global-challenge/"));
        learnList.add(new LearnClass("Issues that need your attention", "2 min. Video", R.drawable.pic5, "Watch now", "https://www.youtube.com/watch?v=8RdB63Is97I&ab_channel=WION"));
        // Add more items to the list as needed

        // Initialize the tips list and the adapter
        tipsList = new ArrayList<>();
        tipsAdapter = new TipsAdapter(getContext(), tipsList);
        tipsRecyclerView.setAdapter(tipsAdapter);

        // Initialize the learn adapter and set it to the RecyclerView
        adapter = new learnAdapter(getContext(), learnList);
        recyclerView.setAdapter(adapter);

        // Fetch tips from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("volunteerTips").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String tip = document.getString("tip");
                        if (tip != null) {
                            tipsList.add(tip);
                        }
                    }
                    tipsAdapter.notifyDataSetChanged(); // Notify the adapter after data is added
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to load tips.", Toast.LENGTH_SHORT).show());

        // Handle "Fill Up Form" button click
        Button fillUpFormButton = view.findViewById(R.id.openFormButton);
        fillUpFormButton.setOnClickListener(v -> {
            // Open the form fragment
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, new learnform());
            transaction.commit();
        });

        return view;
    }
}


