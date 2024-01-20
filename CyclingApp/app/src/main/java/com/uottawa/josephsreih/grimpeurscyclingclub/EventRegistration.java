package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityEventRegistrationBinding;

public class EventRegistration extends AppCompatActivity {
    ActivityEventRegistrationBinding binding;
    DatabaseReference databaseEvents;
    Event event;

    public EventRegistration(){
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        String eventId = getIntent().getStringExtra("EVENT_ID");
        getEvent(eventId);
        binding = ActivityEventRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String firstname = binding.firstName.getText().toString();
                String lastname = binding.lastName.getText().toString();

                if(firstname.equals("") || lastname.equals("")) {
                    Toast.makeText(EventRegistration.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), ParticipantsHomePage.class);
                    int numberOfParticipants = event.getTotalParticipants()+1;
                    if (!eventIsFull(numberOfParticipants)){
                        Toast.makeText(EventRegistration.this, "Registration was successful", Toast.LENGTH_SHORT).show();
                        updateEvent(eventId, numberOfParticipants);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(EventRegistration.this, "Registration was unsuccessful. Event is full", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
            }
        });

    }

    protected void updateEvent(String eventId, int numberOfParticipants) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventId);
        Event newEvent = new Event(event.getEventType(), event.getEventId(), event.getName(), event.getDate(), event.getMaxParticipants(), event.getPrice(), event.getAddress(), event.getEventOrganizer(), numberOfParticipants);
        dR.setValue(newEvent);
    }

    private void getEvent(String eventId) {
        DatabaseReference childNodeRef = databaseEvents.child(eventId);
        childNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    event = dataSnapshot.getValue(Event.class);

                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }
    public boolean eventIsFull(int currentNumOfParticipants){
        if (currentNumOfParticipants>event.getMaxParticipants()){
            return true;
        }
        return false;
    }
}
