package com.example.donorpath;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private TextView homeWelcomeText;
    private RecyclerView recyclerViewLearn, recyclerViewNgo;
    private Button categoryNgo, categoryDonations, categoryVolunteer, categoryLearn;
    private learnAdapter learnAdapter;
    private NgoAdapter ngoAdapter;
    private SharedPreferences sharedPreferences;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        homeWelcomeText = view.findViewById(R.id.home_welcome_text);
        recyclerViewLearn = view.findViewById(R.id.recyclerView);
        recyclerViewNgo = view.findViewById(R.id.recyclerViewNgo);
        categoryNgo = view.findViewById(R.id.category_ngo);
        categoryDonations = view.findViewById(R.id.category_donations);
        categoryVolunteer = view.findViewById(R.id.category_volunteer);
        categoryLearn = view.findViewById(R.id.category_learn);

        // Set up SharedPreferences to fetch the user's name

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("name", "Guest");

        // Set the welcome text with the user's name
        homeWelcomeText.setText("Hello, " + userName);
        // Set up RecyclerView for Learn section (Featured)
        recyclerViewLearn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<LearnClass> learnItems = getFeaturedLearnItems();
        learnAdapter = new learnAdapter(getContext(), learnItems);
        recyclerViewLearn.setAdapter(learnAdapter);

        // Set up RecyclerView for NGO section (Recent Visits)
        recyclerViewNgo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ArrayList<Ngo> ngos = getFeaturedNGOs();
        ngoAdapter = new NgoAdapter(ngos, getParentFragmentManager()); // Corrected
        recyclerViewNgo.setAdapter(ngoAdapter); // Corrected

        // Set up category buttons
        categoryDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open My Donations page
                Toast.makeText(getContext(), "Opening My Donations", Toast.LENGTH_SHORT).show();

                // Replace current fragment with DonationsFragment
                DonationTracker donationsFragment = new DonationTracker();
                getParentFragmentManager().beginTransaction() // Corrected to getParentFragmentManager()
                        .replace(R.id.frame_layout, donationsFragment) // Replace the container with the new fragment
                        .addToBackStack(null) // Add the transaction to the back stack to navigate back
                        .commit();
            }
        });

        categoryVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Volunteer page
                Toast.makeText(getContext(), "Opening Volunteer Section", Toast.LENGTH_SHORT).show();

                // Replace current fragment with VolunteerFragment
                volunteer volunteerFragment = new volunteer();
                getParentFragmentManager().beginTransaction() // Corrected to getParentFragmentManager()
                        .replace(R.id.frame_layout, volunteerFragment) // Replace the container with the new fragment
                        .addToBackStack(null) // Add the transaction to the back stack to navigate back
                        .commit();
            }
        });

        categoryLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Learn page
                Toast.makeText(getContext(), "Opening Learn Section", Toast.LENGTH_SHORT).show();

                // Replace current fragment with LearnFragment
                Learn learnFragment = new Learn();
                getParentFragmentManager().beginTransaction() // Corrected to getParentFragmentManager()
                        .replace(R.id.frame_layout, learnFragment) // Replace the container with the new fragment
                        .addToBackStack(null) // Add the transaction to the back stack to navigate back
                        .commit();
            }
        });

        categoryNgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open NGO Directory page
                Toast.makeText(getContext(), "Opening Ngo Directory Section", Toast.LENGTH_SHORT).show();

                // Replace current fragment with NgoDirectoryFragment
                NgoDirectory ngoFragment = new NgoDirectory();
                getParentFragmentManager().beginTransaction() // Corrected to getParentFragmentManager()
                        .replace(R.id.frame_layout, ngoFragment) // Replace the container with the new fragment
                        .addToBackStack(null) // Add the transaction to the back stack to navigate back
                        .commit();
            }
        });

        return view;
    }

    // Function to populate featured Learn items
    private ArrayList<LearnClass> getFeaturedLearnItems() {
        ArrayList<LearnClass> featuredLearnItems = new ArrayList<>();
        // Example data - Replace this with actual data fetching
        featuredLearnItems.add(new LearnClass("Understanding Global Poverty Better", "5 min . Article", R.drawable.pic4, "Read now", "https://www.technoserve.org/blog/what-is-poverty-understanding-the-global-challenge/")); // Replace with actual data
        return featuredLearnItems;
    }

    // Function to populate featured NGO items
    private ArrayList<Ngo> getFeaturedNGOs() {
        ArrayList<Ngo> featuredNGOs = new ArrayList<>();
        // Example data - Replace this with actual data fetching
        featuredNGOs.add(new Ngo("Marie Stopes International", "Offers reproductive healthcare services and education", "Provides world’s poorest and most vulnerable women with quality family planning and reproductive healthcare. Works with individuals in remote rural areas and in urban slums whose poor access to family planning and reproductive health care only exacerbates their poverty and vulnerability.", R.drawable.logo11, "https://www.msichoices.org/")); // Replace with actual data
        featuredNGOs.add(new Ngo("Fonkoze", "Empowers Haitian communities through microfinance and education", "Haiti’s largest microfinance institution, offering a full range of financial and development services to Haiti’s rural poor. Provides women with the required resources and job training to escape poverty.", R.drawable.logo12, "https://fonkoze.org/")); // Replace with actual data
        return featuredNGOs;
    }
}

