package com.uottawa.josephsreih.grimpeurscyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClubSetEventTypeActivity extends AppCompatActivity {
    int selectedPosition = -1; // Track the selected position

    Button buttonSelectEventType;
    ListView listViewEventTypes;

    List<EventType> eventTypes;
    DatabaseReference databaseEventTypes;
    String currentUserEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_set_event_type);
        currentUserEmail = getIntent().getStringExtra("EMAIL");
        databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");
        listViewEventTypes = findViewById(R.id.listViewEventTypes);
        buttonSelectEventType = findViewById(R.id.buttonSelectEventType);


        eventTypes = new ArrayList<>();
        listViewEventTypes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j < adapterView.getChildCount(); j++) {
                    adapterView.getChildAt(j).setBackgroundColor(0);
                }

                view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                selectedPosition = i;

                buttonSelectEventType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String eventTypeId = eventTypes.get(selectedPosition).getId();
                        Intent intent= new Intent(getApplicationContext(), ClubAddEventActivity.class);
                        intent.putExtra("EVENT_TYPE_ID", eventTypeId);
                        intent.putExtra("EMAIL", currentUserEmail);
                        startActivity(intent);
                    }
                });



                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseEventTypes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventTypes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EventType eventType = postSnapshot.getValue(EventType.class);
                    eventTypes.add(eventType);
                }
                EventTypeList productsAdapter = new EventTypeList(ClubSetEventTypeActivity.this, eventTypes);
                listViewEventTypes.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

