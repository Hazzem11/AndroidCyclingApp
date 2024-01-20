package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EventSearchList extends AppCompatActivity {
    String typeOfEvent;
    String clubName;
    ListView listViewPossibleEvents;
    List<Event> events;
    DatabaseReference databaseEvents;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search_list);
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        events = new ArrayList<>();
        typeOfEvent = getIntent().getStringExtra("EVENT_TYPE");
        listViewPossibleEvents = findViewById(R.id.listViewPossibleEvents);
        listViewPossibleEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j < adapterView.getChildCount(); j++) {
                    adapterView.getChildAt(j).setBackgroundColor(0);
                }

                view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                selectedPosition = i;
                String eventId = events.get(selectedPosition).getEventId();
                Intent intent = new Intent(getApplicationContext(), EventRegistration.class);
                intent.putExtra("EVENT_ID", eventId);
                startActivity(intent);
                return true;
            }
        });

    }

    protected void onStart() {
        super.onStart();
        typeOfEvent = getIntent().getStringExtra("EVENT_TYPE");
        clubName = getIntent().getStringExtra("CLUB_NAME");


        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    if (event != null){
                        if (clubName != null){
                            String compareName = event.getEventOrganizer().getName();
                            String compareTypeOfEvent = event.getEventType().getTypeOfEvent();
                            if (compareName.equals(clubName) && compareTypeOfEvent.equals(typeOfEvent) ) {
                                events.add(event);
                            }
                        }
                        else{
                            String compareTypeOfEvent = event.getEventType().getTypeOfEvent();
                            if (compareTypeOfEvent.equals(typeOfEvent)) {
                                events.add(event);
                            }
                        }
                    }
                }
                EventList productsAdapter = new EventList(EventSearchList.this, events);
                listViewPossibleEvents.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
