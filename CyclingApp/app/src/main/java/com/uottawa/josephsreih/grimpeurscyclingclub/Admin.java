package com.uottawa.josephsreih.grimpeurscyclingclub;

import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Admin {
    private final String username;
    private final String password;
    DatabaseReference databaseEventTypes = FirebaseDatabase.getInstance().getReference("eventTypes");


    public Admin() {
        this.username = "admin";
        this.password = "admin";

    }

    protected void createEventType(EditText editTextEventType, EditText editTextEventDescription) {
        String typeOfEvent = editTextEventType.getText().toString().trim(); // Gets the inputted value from the user in the editText
        String description = editTextEventDescription.getText().toString().trim(); // Gets the inputted value from the user in the editText

        if (!TextUtils.isEmpty(typeOfEvent) && !TextUtils.isEmpty(description)) { // Checks if the entered typeOfEvent and the description value is not null
            String id = databaseEventTypes.push().getKey(); // Creates a unique key for a specific location in the database and assigns that value to id
            EventType eventType = new EventType(typeOfEvent, description, id); // Creates an instance of EventType
            databaseEventTypes.child(id).setValue(eventType); // Sets the created instance of EventType into the specified location (id) of the database
            editTextEventType.setText(""); // Resets the editText to an empty string (Instead of keeping string inputted by user)
            editTextEventDescription.setText(""); // Resets the editText to an empty string (Instead of keeping string inputted by user)
        }

    }

    protected boolean deleteEventType(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("eventTypes").child(id);
        dR.removeValue();
        return true;

    }

    protected void editEventType(String id, EditText editTextEventType, EditText editTextEventDescription) {
        String typeOfEvent = editTextEventType.getText().toString().trim(); // Gets the inputted value from the user in the editText
        String description = editTextEventDescription.getText().toString().trim(); // Gets the inputted value from the user in the editText
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("eventTypes").child(id);
        EventType eventType = new EventType(typeOfEvent, description, id);
        dR.setValue(eventType);
        editTextEventType.setText(""); // Resets the editText to an empty string (Instead of keeping string inputted by user)
        editTextEventDescription.setText(""); // Resets the editText to an empty string (Instead of keeping string inputted by user)

    }

    protected boolean deleteUser(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users").child(id);
        dR.removeValue();
        return true;
    }

}
