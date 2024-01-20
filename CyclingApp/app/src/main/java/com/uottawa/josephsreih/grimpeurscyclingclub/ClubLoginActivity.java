package com.uottawa.josephsreih.grimpeurscyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityClubLoginBinding;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityClubProfileBinding;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityClubSignupBinding;


public class ClubLoginActivity extends AppCompatActivity {

    boolean successfulLogin;
    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");

    public void isValidParticipant(String email, String password, ParticipantLogin.LoginCallback callback) {
        successfulLogin = false;

        databaseUsers.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User with the provided email found in the database
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);

                        // Compare the passwords
                        if (user != null && user.getPassword().equals(password)) {
                            // Passwords match
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            successfulLogin = true;
                            String fullName = user.getName();
                            String typeOfUser = "club owner";
                            callback.onLoginResult(successfulLogin, fullName, typeOfUser);
                        } else {
                            // Passwords don't match
                            Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
                            successfulLogin = false;
                            callback.onLoginResult(successfulLogin, null, null);
                        }
                    }
                } else {
                    // User with the provided email not found in the database
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                    successfulLogin = false;
                    callback.onLoginResult(successfulLogin, null, null);
                }

                // Notify the callback about the login result
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error querying database", Toast.LENGTH_SHORT).show();

                // Notify the callback about the login result
                callback.onLoginResult(false, null, null);
            }
        });
    }

    ActivityClubLoginBinding clubLogin;

    ActivityClubSignupBinding clubSignup;

    ActivityClubProfileBinding clubProfile;

    String role = "Club";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clubLogin = ActivityClubLoginBinding.inflate(getLayoutInflater());
        clubSignup = ActivityClubSignupBinding.inflate(getLayoutInflater());
        clubProfile = ActivityClubProfileBinding.inflate(getLayoutInflater());
        setContentView(clubLogin.getRoot());


        clubLogin.loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = clubLogin.loginEmail.getText().toString();
                String password = clubLogin.loginPassword.getText().toString();


                if (email.equals("") || password.equals("")) {
                    Toast.makeText(ClubLoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    isValidParticipant(email, password, new ParticipantLogin.LoginCallback() {
                        @Override
                        public void onLoginResult(boolean success, String fullName, String typeOfUser) {
                            if (success) {
                                Toast.makeText(ClubLoginActivity.this, "Login was successful", Toast.LENGTH_SHORT).show();
                                Intent intent;
                                intent = new Intent(getApplicationContext(), ClubProfileActivity.class);
                                intent.putExtra("EMAIL", email);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ClubLoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        clubLogin.signupRedirectTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClubLoginActivity.this, ClubSignupActivity.class);
                startActivity(intent);

            }
        });
    }

}
