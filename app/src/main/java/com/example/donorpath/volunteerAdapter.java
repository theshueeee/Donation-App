package com.example.donorpath;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
public class volunteerAdapter extends RecyclerView.Adapter<volunteerAdapter.NgoViewHolder> {

    private List<Ngo> ngoList; // List of NGOs
    private final Context context; // Context to open the URL in browser

    public volunteerAdapter(List<Ngo> ngoList, Context context) {
        this.ngoList = ngoList;
        this.context = context;
    }

    @NonNull
    @Override
    public NgoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ngo_card, parent, false);
        return new NgoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NgoViewHolder holder, int position) {
        Ngo ngo = ngoList.get(position);

        // Bind NGO data to the views
        holder.nameTextView.setText(ngo.getName());
        holder.descriptionTextView.setText(ngo.getDescription());
        holder.imageView.setImageResource(ngo.getImageResourceId());

        // Handle "View More" button click to open the NGO's official page
        holder.viewMoreButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ngo.getWebsiteUrl()));
            context.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return ngoList.size();
    }

    // Update the list of NGOs dynamically
    public void updateList(List<Ngo> newList) {
        ngoList = newList;
        notifyDataSetChanged();
    }

    // ViewHolder class for NGO cards
    static class NgoViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descriptionTextView;
        ImageView imageView;
        Button viewMoreButton;

        public NgoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textNgoName);
            descriptionTextView = itemView.findViewById(R.id.textNgoDescription);
            imageView = itemView.findViewById(R.id.imageNgo);
            viewMoreButton = itemView.findViewById(R.id.buttonViewMore);
        }
    }
}




