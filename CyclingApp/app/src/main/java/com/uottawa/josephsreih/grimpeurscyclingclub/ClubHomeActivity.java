package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityClubHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class ClubHomeActivity extends AppCompatActivity {

    ActivityClubHomeBinding binding;
    int selectedPosition;
    DatabaseReference databaseEvents;
    DatabaseReference databaseUsers;
    ListView listViewEvents;
    List<Event> events;
    Button deleteEvent;
    Button editEvent;
    String currentUserEmail;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listViewEvents = findViewById(R.id.listViewEvents);
        events = new ArrayList<>();
        currentUserEmail = getIntent().getStringExtra("EMAIL");

        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        deleteEvent = findViewById(R.id.deleteEvent);
        editEvent = findViewById(R.id.editEvent);


        binding.addEventClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClubSetEventTypeActivity.class);
                intent.putExtra("EMAIL", currentUserEmail);
                startActivity(intent);

            }
        });
        listViewEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            for (int j = 0; j < adapterView.getChildCount(); j++) {
                adapterView.getChildAt(j).setBackgroundColor(0);
            }

            view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

            selectedPosition = i;

            binding.deleteEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedPosition != -1) {
                        deleteEvent(events.get(selectedPosition).getEventId());
                        clearSelection();
                    }
                }
            });

            binding.editEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedPosition != -1) {
                        Intent intent = new Intent(getApplicationContext(), ClubEditEventActivity.class);
                        String eventId = events.get(selectedPosition).getEventId();
                        intent.putExtra("EMAIL", currentUserEmail);
                        intent.putExtra("EVENT_ID", eventId);
                        startActivity(intent);
                    }
                }
            });

            return true;
        }
    });

    }
    private void clearSelection() {
        selectedPosition = -1;
        for (int i = 0; i < listViewEvents.getChildCount(); i++) {
            listViewEvents.getChildAt(i).setBackgroundColor(0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUserEmail = getIntent().getStringExtra("EMAIL");
        Query query = databaseUsers.orderByChild("email").equalTo(currentUserEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        currentUser = snapshot.getValue(User.class);
                        databaseEvents.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                events.clear();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Event event = postSnapshot.getValue(Event.class);
                                    if (event!= null){
                                        String compareKey = event.getEventOrganizer().getId();
                                        if (compareKey.equals(currentUser.getId())) {
                                            events.add(event);

                                        }
                                    }
                                }
                                EventList productsAdapter = new EventList(ClubHomeActivity.this, events);
                                listViewEvents.setAdapter(productsAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
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
    public boolean deleteEvent(String id){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(id);
        dR.removeValue();
        return true;
    }


}
