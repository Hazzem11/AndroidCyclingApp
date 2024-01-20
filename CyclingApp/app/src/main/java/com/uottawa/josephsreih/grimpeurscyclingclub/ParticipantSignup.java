package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityParticipantSignupBinding;

import java.util.ArrayList;
import java.util.List;

public class ParticipantSignup extends AppCompatActivity {

    ActivityParticipantSignupBinding binding;
    DatabaseReference databaseParticipants = FirebaseDatabase.getInstance().getReference("users");

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParticipantSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String firstname = binding.firstname.getText().toString();
                String lastname = binding.lastname.getText().toString();
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupPasswordConfirm.getText().toString();

                if(firstname.equals("") || lastname.equals("")||email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(ParticipantSignup.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(confirmPassword)){
                        Boolean valid = isValidEmail(email);
                        if(valid){
                            addParticipant(firstname, lastname, email, password);
                            Toast.makeText(ParticipantSignup.this, "Signup was successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ParticipantLogin.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ParticipantSignup.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(ParticipantSignup.this, "Passwords don't match", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
    private void addParticipant(String firstname, String lastname, String email, String password){
        String id = databaseParticipants.push().getKey();
        Participant participant = new Participant(firstname, lastname, email, password, id);
        databaseParticipants.child(id).setValue(participant);

    }
}
