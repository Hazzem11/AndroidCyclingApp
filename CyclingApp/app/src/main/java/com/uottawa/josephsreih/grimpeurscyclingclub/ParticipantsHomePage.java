package com.uottawa.josephsreih.grimpeurscyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityParticipantsHomePageBinding;


import android.widget.Toast;

import com.google.firebase.database.Query;




public class ParticipantsHomePage extends AppCompatActivity {

    ActivityParticipantsHomePageBinding binding;
    String timeTrial = "Time Trial";
    String groupRides = "Group Rides";
    String hillClimb = "Hill Climb";
    DatabaseReference databaseUsers;

    private DatabaseReference databaseEvents;

    private ActivityParticipantsHomePageBinding participantsHomePage;
    public interface ClubNameCheckCallback {
        void onClubNameChecked(boolean exists);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_home_page);
        binding = ActivityParticipantsHomePageBinding.inflate(getLayoutInflater());
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        setContentView(binding.getRoot());




        // Initialize Firebase Database reference
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");


        participantsHomePage = ActivityParticipantsHomePageBinding.inflate(getLayoutInflater());

        // Set up OnClickListener
        participantsHomePage.rateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start RateClub activity
                Intent intent = new Intent(getApplicationContext(), RateClub.class);
                startActivity(intent);
            }
        });



        binding = ActivityParticipantsHomePageBinding.inflate(getLayoutInflater());
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        setContentView(binding.getRoot());


        binding.timeTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredClubName = binding.autoCompleteTextView.getText().toString();
                if(!enteredClubName.equals("")) {
                    checkClubName(enteredClubName, new ClubNameCheckCallback() {
                        @Override
                        public void onClubNameChecked(boolean exists) {
                            if (exists) {
                                Intent intent = new Intent(getApplicationContext(), EventSearchList.class);
                                intent.putExtra("EVENT_TYPE", timeTrial);
                                intent.putExtra("CLUB_NAME", enteredClubName);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ParticipantsHomePage.this, "Club does not exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
                }else{
                    Intent intent = new Intent(getApplicationContext(), EventSearchList.class);
                    intent.putExtra("EVENT_TYPE", timeTrial);
                    startActivity(intent);
                }

            }
        });
        binding.hillClimbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredClubName = binding.autoCompleteTextView.getText().toString();
                if(!enteredClubName.equals("")) {
                    checkClubName(enteredClubName, new ClubNameCheckCallback() {
                        @Override
                        public void onClubNameChecked(boolean exists) {
                            if (exists) {
                                Intent intent = new Intent(getApplicationContext(), EventSearchList.class);
                                intent.putExtra("EVENT_TYPE", hillClimb);
                                intent.putExtra("CLUB_NAME", enteredClubName);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ParticipantsHomePage.this, "Club does not exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
                }else{
                    Intent intent = new Intent(getApplicationContext(), EventSearchList.class);
                    intent.putExtra("EVENT_TYPE", hillClimb);
                    startActivity(intent);
                }

            }
        });
        binding.groupRidesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredClubName = binding.autoCompleteTextView.getText().toString();
                if(!enteredClubName.equals("")) {
                    checkClubName(enteredClubName, new ClubNameCheckCallback() {
                        @Override
                        public void onClubNameChecked(boolean exists) {
                            if (exists) {
                                Intent intent = new Intent(getApplicationContext(), EventSearchList.class);
                                intent.putExtra("EVENT_TYPE", groupRides);
                                intent.putExtra("CLUB_NAME", enteredClubName);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ParticipantsHomePage.this, "Club does not exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
                }else{
                    Intent intent = new Intent(getApplicationContext(), EventSearchList.class);
                    intent.putExtra("EVENT_TYPE", groupRides);
                    startActivity(intent);
                }

            }
        });
        binding.rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RateClub.class);
                startActivity(intent);

            }
        });

    }

    public void checkClubName(String enteredClubName, ClubNameCheckCallback callback) {
        Query query = databaseUsers.orderByChild("name").equalTo(enteredClubName);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Club name exists in the database
                    callback.onClubNameChecked(true);
                } else {
                    // Club name does not exist in the database
                    callback.onClubNameChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
