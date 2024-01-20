package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityParticipantLoginBinding;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityParticipantSignupBinding;


public class ParticipantLogin extends AppCompatActivity {
    public interface LoginCallback {
        void onLoginResult(boolean success, String fullName, String typeOfUser);
    }

    ActivityParticipantLoginBinding binding;
    ActivityParticipantSignupBinding binding2;
    String role = "Participant";
    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
    boolean successfulLogin;


    public void isValidParticipant(String email, String password, LoginCallback callback) {
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
                            String typeOfUser = "participant";
                            callback.onLoginResult(successfulLogin, fullName, typeOfUser);
                        } else {
                            // Passwords don't match
                            Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
                            successfulLogin = false;
                            callback.onLoginResult(false, null, null);
                        }
                    }
                } else {
                    // User with the provided email not found in the database
                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                    successfulLogin = false;
                    callback.onLoginResult(false, null, null);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParticipantLoginBinding.inflate(getLayoutInflater());
        binding2 = ActivityParticipantSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(ParticipantLogin.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    isValidParticipant(email, password, new LoginCallback() {
                        @Override
                        public void onLoginResult(boolean success, String fullName, String typeOfUser) {
                            if (success) {
                                Toast.makeText(ParticipantLogin.this, "Login was successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ParticipantsHomePage.class);
                                intent.putExtra("FULL_NAME", fullName);
                                intent.putExtra("TYPE_OF_USER", typeOfUser);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ParticipantLogin.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        binding.signupRedirectTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ParticipantLogin.this, ParticipantSignup.class);
                startActivity(intent);

            }
        });    }

}
