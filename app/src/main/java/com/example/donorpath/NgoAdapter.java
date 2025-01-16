package com.example.donorpath;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NgoAdapter extends RecyclerView.Adapter<NgoAdapter.NgoViewHolder> {

    private List<Ngo> ngoList; // List of NGOs
    private final FragmentManager fragmentManager;

    public NgoAdapter(List<Ngo> ngoList, FragmentManager fragmentManager) {
        this.ngoList = ngoList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public NgoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the NGO card
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ngo_card, parent, false);
        return new NgoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NgoViewHolder holder, int position) {
        Ngo ngo = ngoList.get(position);

        // Bind the NGO data to the views in the card
        holder.nameTextView.setText(ngo.getName());
        holder.descriptionTextView.setText(ngo.getDescription());
        holder.imageView.setImageResource(ngo.getImageResourceId());

        // Handle "View More" button click
        holder.viewMoreButton.setOnClickListener(v -> {
            // Create a new instance of the NgoDescriptionFragment
            NgoDescription fragment = new NgoDescription();

            // Prepare a Bundle to pass data to the fragment
            Bundle bundle = new Bundle();
            bundle.putString("name", ngo.getName());
            bundle.putString("about", ngo.getAbout());
            bundle.putInt("logoResId", ngo.getImageResourceId());
            bundle.putString("ngoUrl", ngo.getWebsiteUrl());  // Pass the NGO's URL for donation
            fragment.setArguments(bundle);

            // Replace the current fragment with the new fragment in the container (use proper container ID)
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, fragment)  // Replace with the container's ID
                    .addToBackStack(null)  // Add transaction to back stack so that user can navigate back
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return ngoList.size();  // Return the total number of NGOs in the list
    }

    /**
     * Update the list of NGOs dynamically.
     * @param newList - The new list to display.
     */
    public void updateList(List<Ngo> newList) {
        ngoList = newList;
        notifyDataSetChanged();  // Notify the adapter that the data has changed
    }

    // ViewHolder class to hold references to the views inside the NGO card
    static class NgoViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descriptionTextView;
        ImageView imageView;
        Button viewMoreButton;

        public NgoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find views by their IDs
            nameTextView = itemView.findViewById(R.id.textNgoName);
            descriptionTextView = itemView.findViewById(R.id.textNgoDescription);
            imageView = itemView.findViewById(R.id.imageNgo);
            viewMoreButton = itemView.findViewById(R.id.buttonViewMore);
        }
    }
}






