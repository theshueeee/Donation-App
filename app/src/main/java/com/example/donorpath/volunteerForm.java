package com.example.donorpath;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class volunteerForm extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_form, container, false);

        EditText nameField = view.findViewById(R.id.nameInput);
        EditText emailField = view.findViewById(R.id.emailInput);
        EditText reasonField = view.findViewById(R.id.reasonInput);

        Button submitButton = view.findViewById(R.id.submitFormButton);
        submitButton.setOnClickListener(v -> {
            // Display a toast and return to the previous fragment
            Toast.makeText(getContext(), "Thank you for submitting!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack(); // Return to previous fragment
        });

        return view;
    }
}
