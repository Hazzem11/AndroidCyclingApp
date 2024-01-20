package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityClubAddEventBinding;


public class ClubAddEventActivity extends AppCompatActivity {

    DatabaseReference databaseEvents;
    DatabaseReference databaseEventTypes;

    Button buttonAddEvent;
    private String nameOfEvent;
    private String eventDate;
    private String participantText;
    private String priceText;
    private String eventLocation;
    private int eventNumberOfParticipants;
    private double eventPrice;
    ActivityClubAddEventBinding binding;
    EventType eventType;
    User currentUser;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_add_event);

        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        String eventTypeId = getIntent().getStringExtra("EVENT_TYPE_ID");
        String email = getIntent().getStringExtra("EMAIL");


        binding = ActivityClubAddEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getEventType(eventTypeId);
        buttonAddEvent = findViewById(R.id.addEventButton);
        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameOfEvent = binding.addTextNameOfEvent.getText().toString();
                eventDate = binding.addTextEventDate.getText().toString();
                participantText = binding.addTextEventNumberOfParticipants.getText().toString();
                priceText = binding.addTextEventPrice.getText().toString();
                eventLocation = binding.addTextEventLocation.getText().toString();


                if (nameOfEvent.equals("") || eventDate.equals("") || eventLocation.equals("") || participantText.equals("") || priceText.equals("")) {
                    Toast.makeText(ClubAddEventActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    Query query = databaseUsers.orderByChild("email").equalTo(email);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    currentUser = snapshot.getValue(User.class);
                                    if (currentUser != null){
                                        try {
                                            eventPrice = Double.parseDouble(priceText);
                                            eventNumberOfParticipants = Integer.parseInt(participantText);

                                            Intent intent = new Intent(getApplicationContext(), ClubHomeActivity.class);
                                            Address eventAddress = new Address(eventLocation, null);
                                            intent.putExtra("EMAIL", email);
                                            addEvent(nameOfEvent, eventDate, eventNumberOfParticipants, eventPrice, eventAddress);
                                            startActivity(intent);
                                        } catch (NumberFormatException e) {
                                            Toast.makeText(ClubAddEventActivity.this, "Invalid event price. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    return;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors
                        }
                    });

                }

            }
            });

        }

    private void addEvent(String nameOfEvent, String eventDate, int eventNumberOfParticipants, double eventPrice, Address eventAddress){
        String id = databaseEvents.push().getKey();
        Event event = new Event(eventType , id, nameOfEvent, eventDate, eventNumberOfParticipants, eventPrice, eventAddress, currentUser, 0);
        databaseEvents.child(id).setValue(event);
    }



    private void getEventType(String eventTypeId){
        DatabaseReference childNodeRef = databaseEventTypes.child(eventTypeId);
        childNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                eventType = dataSnapshot.getValue(EventType.class);

            }
        }
        public void onCancelled(DatabaseError databaseError){
        }

    });

    }



}

