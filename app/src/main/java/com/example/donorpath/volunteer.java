package com.example.donorpath;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;



import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class volunteer extends Fragment {

    private List<Ngo> ngoList; // Full list of NGOs
    private volunteerAdapter adapter; // Adapter for RecyclerView

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewVolunteer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EditText searchBar = view.findViewById(R.id.search_bar);

        // Temporary static list of NGOs
        ngoList = new ArrayList<>();
        ngoList.add(new Ngo("Acumen", "Invests in sustainable businesses to tackle poverty", " ",R.drawable.logo1, "https://app.acumenacademy.org/malaysia-acumen-fellowship"));
        ngoList.add(new Ngo("Unicef - Malaysia", "Advocates for children's rights", " ",R.drawable.logo2, "https://www.unicef.org/careers/volunteers-unicef"));
        ngoList.add(new Ngo("Save the Children", "Supports children in need", " ",R.drawable.logo3, "https://www.savethechildren.org/us/ways-to-help/how-to-volunteer"));

        // Initialize adapter and RecyclerView
        adapter = new volunteerAdapter(ngoList, getContext());
        recyclerView.setAdapter(adapter);

        // TextWatcher to filter results
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        // Handle "Fill Up Form" button click
        Button fillUpFormButton = view.findViewById(R.id.openFormButton);
        fillUpFormButton.setOnClickListener(v -> {
            // Open the form fragment
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, new volunteerForm());
            transaction.addToBackStack(null); // Add to backstack so user can navigate back
            transaction.commit();
        });

        return view;
    }

    // Filter method to search NGO names
    private void filter(String text) {
        List<Ngo> filteredList = new ArrayList<>();
        for (Ngo ngo : ngoList) {
            if (ngo.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(ngo);
            }
        }
        adapter.updateList(filteredList);
    }
}




