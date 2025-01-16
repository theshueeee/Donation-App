package com.example.donorpath;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
public class Login extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.btn_login);
        signupRedirectText = findViewById(R.id.singupRedirectText);

        firestore = FirebaseFirestore.getInstance();  // Initialize Firestore

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {
                    // Do nothing if validation fails
                } else {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, signup.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        // Query Firestore for user data based on the username
        firestore.collection("users")
                .whereEqualTo("username", userUsername)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot documentSnapshots = task.getResult();
                        if (!documentSnapshots.isEmpty()) {
                            // User exists
                            DocumentSnapshot document = documentSnapshots.getDocuments().get(0);
                            String passwordFromDB = document.getString("password");

                            if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                                // Password matches
                                String nameFromDB = document.getString("name");
                                String emailFromDB = document.getString("email");
                                String usernameFromDB = document.getString("username");

                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("name", nameFromDB);
                                intent.putExtra("email", emailFromDB);
                                intent.putExtra("username", usernameFromDB);
                                intent.putExtra("password", passwordFromDB);

                                startActivity(intent);
                            } else {
                                loginPassword.setError("Invalid Credentials");
                                loginPassword.requestFocus();
                            }
                        } else {
                            loginUsername.setError("User does not exist");
                            loginUsername.requestFocus();
                        }
                    } else {
                        // Handle error if the query fails
                    }
                });
    }
}
