package com.uottawa.josephsreih.grimpeurscyclingclub;

import android.text.TextUtils;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CyclingClub extends User {

    private String clubName;
    private String email;
    private String password;
    private String id;
    private List<Event> eventList;

    public CyclingClub(String name, String email, String password, String id) {
        super(name, email, password, id);
        this.clubName = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }


    // Getter for clubName
    public String getClubName() {
        return clubName;
    }

    // Setter for clubName
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    // Getter for email (if needed, since it's already in the superclass User)
    @Override
    public String getEmail() {
        return email;
    }

    // Setter for email (if needed, since it's already in the superclass User)

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for password (if needed, since it's already in the superclass User)
    @Override
    public String getPassword() {
        return password;
    }

    // Setter for password (if needed, since it's already in the superclass User)

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for id (if needed, since it's already in the superclass User)
    @Override
    public String getId() {
        return id;
    }

    // Setter for id (if needed, since it's already in the superclass User)
    public void setId(String id) {
        this.id = id;
    }


}
