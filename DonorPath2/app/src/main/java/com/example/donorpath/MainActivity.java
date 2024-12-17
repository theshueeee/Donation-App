package com.example.donorpath;

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

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.navView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            binding.drawerLayout.closeDrawer(GravityCompat.START);

            if (itemId == R.id.nav_profile) {
                replaceFragment(new profilepage());
            } else if (itemId == R.id.nav_ngo_directory) {
                replaceFragment(new NgoDirectory());
            } else if (itemId == R.id.nav_volunteer) {
                replaceFragment(new volunteer());
            } else if (itemId == R.id.nav_learn) {
                replaceFragment(new Learn());
            } else if (itemId == R.id.nav_tracker) {
                replaceFragment(new DonationTracker());
            } else if (itemId == R.id.nav_logout) {
                // Handle logout logic
            }
            return true;
        });

        // Set the default fragment
        replaceFragment(new Home());

        // Handle Bottom Navigation View item selection
        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new Home());
            } else if (itemId == R.id.ngo_directory) {
                replaceFragment(new NgoDirectory());
            } else if (itemId == R.id.profile) {
                replaceFragment(new profilepage());
            }

            return true;
        });


        // Configure edge-to-edge UI and insets
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Method to replace the fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit(); // Correct usage of commit()
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
