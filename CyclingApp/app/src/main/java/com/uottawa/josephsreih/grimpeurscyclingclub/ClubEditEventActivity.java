package com.uottawa.josephsreih.grimpeurscyclingclub;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityClubAddEventBinding;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityClubEditEventBinding;


public class ClubEditEventActivity extends AppCompatActivity {

    DatabaseReference databaseEvents;
    DatabaseReference databaseEventTypes;

    Button editEventButton;
    private String nameOfEvent;
    private String eventDate;
    private String participantText;
    private String priceText;
    private String eventLocation;
    private int eventNumberOfParticipants;
    private double eventPrice;
    ActivityClubEditEventBinding binding;
    Event event;
    String eventId;
    String email;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_edit_event);
        binding = ActivityClubEditEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        eventId = getIntent().getStringExtra("EVENT_ID");
        email = getIntent().getStringExtra("EMAIL");
        getEvent(eventId);





        binding.editEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameOfEvent = binding.addTextNameOfEvent.getText().toString();
                eventDate = binding.addTextEventDate.getText().toString();
                participantText = binding.addTextEventNumberOfParticipants.getText().toString();
                priceText = binding.addTextEventPrice.getText().toString();
                eventLocation = binding.addTextEventLocation.getText().toString();
                try {
                    if (nameOfEvent.equals("") || eventDate.equals("") || eventLocation.equals("") || participantText.equals("") || priceText.equals("")) {
                        Toast.makeText(ClubEditEventActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    } else {

                        eventPrice = Double.parseDouble(priceText);
                        eventNumberOfParticipants = Integer.parseInt(participantText);

                        Intent intent = new Intent(getApplicationContext(), ClubHomeActivity.class);
                        Address eventAddress = new Address(eventLocation, null);
                        intent.putExtra("EMAIL", email);
                        editEvent(nameOfEvent, eventDate, eventNumberOfParticipants, eventPrice, eventAddress);
                        startActivity(intent);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(ClubEditEventActivity.this, "Invalid event price. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }






    private void editEvent(String nameOfEvent, String eventDate, int eventNumberOfParticipants, double eventPrice, Address eventAddress){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventId);
        Event EditedEvent = new Event(event.getEventType(), eventId, nameOfEvent, eventDate, eventNumberOfParticipants, eventPrice, eventAddress, event.getEventOrganizer(), event.getTotalParticipants());
        dR.setValue(EditedEvent);
    }
    private void getEvent(String eventId) {
        DatabaseReference childNodeRef = databaseEvents.child(eventId);
        childNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    event = dataSnapshot.getValue(Event.class);
                    TextView addTextNameOfEvent = findViewById(R.id.addTextNameOfEvent);
                    TextView addTextEventDate = findViewById(R.id.addTextEventDate);
                    TextView addTextEventNumberOfParticipants = findViewById(R.id.addTextEventNumberOfParticipants);
                    TextView addTextEventPrice = findViewById(R.id.addTextEventPrice);
                    TextView addTextEventLocation = findViewById(R.id.addTextEventLocation);

                    addTextNameOfEvent.setText(event.getName());
                    addTextEventDate.setText(event.getDate());
                    addTextEventNumberOfParticipants.setText(String.valueOf(event.getMaxParticipants()));
                    addTextEventPrice.setText(String.valueOf(event.getPrice()));
                    addTextEventLocation.setText(event.getAddress().getLocation());

                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }
}


