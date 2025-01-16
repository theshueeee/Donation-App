package com.example.donorpath;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profilepage#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class profilepage extends Fragment {
    private TextView profileTitleName, profileName, profileEmail, profilePassword;
    private Button editProfileButton, logoutButton;
    private String name, email, password, username;
    private FirebaseUser currentUser; // To get the current logged-in user

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profilepage, container, false);

        // Initialize TextViews
        profileTitleName = view.findViewById(R.id.profile_title_name); // Title name
        profileName = view.findViewById(R.id.profile_name);
        profileEmail = view.findViewById(R.id.profile_email);
        profilePassword = view.findViewById(R.id.profile_password);

        // Initialize buttons
        editProfileButton = view.findViewById(R.id.edit_profile_button);
        logoutButton = view.findViewById(R.id.logout_button);

        // Initialize FirebaseUser
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Retrieve data passed from previous activity or fragment
        if (getArguments() != null) {
            name = getArguments().getString("name");
            email = getArguments().getString("email");
            password = getArguments().getString("password");

            // Set the retrieved data to the TextViews
            profileTitleName.setText("Hello, " + name); // Display a greeting with the name
            profileName.setText(name);
            profileEmail.setText(email);
            profilePassword.setText(password);
        }

        // Edit profile logic
        editProfileButton.setOnClickListener(v -> {
            // Create an instance of the updateProfile fragment
            updateProfile updateProfileFragment = new updateProfile();

            // Pass the current profile data to the updateProfile fragment
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("email", email);
            bundle.putString("password", password);
            bundle.putString("username", username);
            updateProfileFragment.setArguments(bundle);

            // Replace the current fragment with the updateProfile fragment
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, updateProfileFragment);
            fragmentTransaction.addToBackStack(null); // Add to back stack to allow back navigation
            fragmentTransaction.commit();
        });


        // Logout logic
        logoutButton.setOnClickListener(v -> {
            // Clear user session (for example, shared preferences or database)
            // For now, we'll just show a Toast and redirect to LoginActivity
            Toast.makeText(getContext(), "Logging out...", Toast.LENGTH_SHORT).show();

            // Optionally, clear stored data like shared preferences or session info
            // SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            // prefs.edit().clear().apply();

            // Sign out the user from Firebase Authentication
            FirebaseAuth.getInstance().signOut();

            // Redirect to login activity (replace with actual login activity)
            Intent intent = new Intent(getActivity(), Login.class); // Replace with actual login activity
            startActivity(intent);
            getActivity().finish(); // Close the current activity
        });

        return view;
    }

    // Handle the result from EditProfileActivity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Retrieve the updated profile data from the result
            name = data.getStringExtra("name");
            email = data.getStringExtra("email");
            password = data.getStringExtra("password");

            // Update the profile details on the profile page
            profileName.setText(name);
            profileEmail.setText(email);
            profilePassword.setText(password);
            profileTitleName.setText("Hello, " + name); // Update the title name
        }
    }
}





