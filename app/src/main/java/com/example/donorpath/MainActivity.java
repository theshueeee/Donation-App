package com.example.donorpath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.donorpath.databinding.ActivityMainBinding;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.donorpath.databinding.ActivityMainBinding;  // Use your binding class
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private DatabaseHelper currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeUserData();

        setSupportActionBar(binding.toolbar);
        toggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.open_nav,
                R.string.close_nav
        );
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Pass user data to Home fragment on initial load
        Home homeFragment = new Home();
        Bundle homeBundle = new Bundle();
        homeBundle.putString("name", currentUser.getName());
        homeFragment.setArguments(homeBundle);
        replaceFragment(homeFragment);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        configureNavigation();
    }

    private void initializeUserData() {
        // Get user data from intent
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        currentUser = new DatabaseHelper(name, email, username, password);

        // Store user data in SharedPreferences
        // So that the information of the user can be passed on to other pages.
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    private void configureNavigation() {
        // Handle navigation drawer item selection
        binding.navView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            binding.drawerLayout.closeDrawer(GravityCompat.START);

            Fragment targetFragment = null;

            if (itemId == R.id.nav_profile) {
                profilepage profileFragment = new profilepage();
                targetFragment = profileFragment;
            } else if (itemId == R.id.nav_ngo_directory) {
                targetFragment = new NgoDirectory();
            } else if (itemId == R.id.nav_volunteer) {
                targetFragment = new volunteer();
            } else if (itemId == R.id.nav_learn) {
                targetFragment = new Learn();
            } else if (itemId == R.id.nav_tracker) {
                targetFragment = new DonationTracker();
            } else if (itemId == R.id.nav_logout) {
                // Handle logout logic here
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Redirect to LoginActivity
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
                startActivity(intent);

                // Finish MainActivity
                finish();
            }

            if (targetFragment != null) {
                replaceFragment(targetFragment);
            }

            return true;
        });

        // Handle Bottom Navigation View item selection
        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            Fragment targetFragment = null;

            if (itemId == R.id.home) {
                Home homeFragment = new Home();
                Bundle homeBundle = new Bundle();
                homeBundle.putString("name", currentUser.getName());
                homeFragment.setArguments(homeBundle);
                targetFragment = homeFragment;
            } else if (itemId == R.id.ngo_directory) {
                targetFragment = new NgoDirectory();
            } else if (itemId == R.id.profile) {
                profilepage profileFragment = new profilepage();
                targetFragment = profileFragment;
            }

            if (targetFragment != null) {
                replaceFragment(targetFragment);
            }

            return true;
        });
    }

    // Method to replace the fragment
    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            // If the fragment needs user data, pass it through bundle
            Bundle bundle = fragment.getArguments();
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("name", currentUser.getName());
            bundle.putString("email", currentUser.getEmail());
            bundle.putString("username", currentUser.getUsername());
            bundle.putString("password", currentUser.getPassword());
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commit();
        }
    }

    public DatabaseHelper getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}



