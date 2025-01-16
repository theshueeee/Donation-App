package com.example.donorpath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipViewHolder> {
    private Context context;
    private List<String> tipList;  // Corrected to String

    public TipsAdapter(Context context, List<String> tipList) {
        this.context = context;
        this.tipList = tipList;
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_tip_card, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        String tip = tipList.get(position);
        // Bind data to the view holder
        // Assuming there's a TextView in your item layout to display the tip
        holder.tipTextView.setText(tip);
    }

    @Override
    public int getItemCount() {
        return tipList.size();
    }

    public class TipViewHolder extends RecyclerView.ViewHolder {
        TextView tipTextView;  // Assuming this is the TextView in item layout

        public TipViewHolder(View itemView) {
            super(itemView);
            tipTextView = itemView.findViewById(R.id.tipText);  // Initialize the TextView
        }
    }
}


