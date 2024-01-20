package com.uottawa.josephsreih.grimpeurscyclingclub;

import java.util.List;

public class Participant extends User {

    public Participant(String firstName, String lastName, String email, String password, String id) {
        super(firstName + " " + lastName, email, password, id);
    }
}
