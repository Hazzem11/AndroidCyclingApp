package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditEventTypesActivity extends AppCompatActivity {

    Admin admin = new Admin();
    EditText editTextEventType;
    EditText editTextEventDescription;
    Button buttonAddEventType;
    ListView listViewEventTypes;

    List<EventType> eventTypes;
    DatabaseReference databaseEventTypes;
    Button editEventTypeButton;
    Button deleteEventTypeButton;

    int selectedPosition = -1; // Track the selected position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event_types);
        databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");
        editTextEventType = findViewById(R.id.editTextEventType);
        editTextEventDescription = findViewById(R.id.editTextEventDescription);
        listViewEventTypes = findViewById(R.id.listViewEventTypes);
        buttonAddEventType = findViewById(R.id.addEventTypeButton);
        editEventTypeButton = findViewById(R.id.editEventTypeButton);
        deleteEventTypeButton = findViewById(R.id.deleteEventTypeButton);

        eventTypes = new ArrayList<>();


        buttonAddEventType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin.createEventType(editTextEventType, editTextEventDescription);
            }
        });

        listViewEventTypes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j < adapterView.getChildCount(); j++) {
                    adapterView.getChildAt(j).setBackgroundColor(0);
                }

                view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                selectedPosition = i;

                editEventTypeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String typeOfEvent = editTextEventType.getText().toString().trim();
                        if (!TextUtils.isEmpty(typeOfEvent) && selectedPosition != -1) {
                            admin.editEventType(eventTypes.get(selectedPosition).getId(), editTextEventType, editTextEventDescription);
                            clearSelection();
                        }
                    }
                });

                deleteEventTypeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition != -1) {
                            admin.deleteEventType(eventTypes.get(selectedPosition).getId());
                            clearSelection();
                        }
                    }
                });

                return true;
            }
        });
    }

    private void clearSelection() {
        selectedPosition = -1;
        for (int i = 0; i < listViewEventTypes.getChildCount(); i++) {
            listViewEventTypes.getChildAt(i).setBackgroundColor(0);
        }
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
                EventTypeList productsAdapter = new EventTypeList(EditEventTypesActivity.this, eventTypes);
                listViewEventTypes.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
