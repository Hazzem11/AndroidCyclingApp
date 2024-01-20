package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityClubSignupBinding;

import java.util.ArrayList;
import java.util.List;

public class ClubSignupActivity extends AppCompatActivity {

    ActivityClubSignupBinding binding;

    CyclingClub club;
    DatabaseReference databaseCyclingClubs = FirebaseDatabase.getInstance().getReference("users");
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValidEmail(String target) {
        if (target.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void addCyclingClub(String name, String email, String password){
        String id = databaseCyclingClubs.push().getKey();
        club = new CyclingClub(name, email, password, id);
        databaseCyclingClubs.child(id).setValue(club);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String username = binding.signupName.getText().toString();
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupPasswordConfirm.getText().toString();

                if(username.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(ClubSignupActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(confirmPassword)){
                        Boolean valid = isValidEmail(email);
                        if(valid){
                            addCyclingClub(username, email, password);
                            Toast.makeText(ClubSignupActivity.this, "Signup was successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ClubLoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ClubSignupActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
    }}

