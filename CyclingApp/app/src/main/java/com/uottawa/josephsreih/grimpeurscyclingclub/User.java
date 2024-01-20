package com.uottawa.josephsreih.grimpeurscyclingclub;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;

    private String email;

    private String password;
    private String id;
    private List<Event> eventList;
    public User(){
        this.eventList = new ArrayList<>();
    }
    public User(String name, String email, String password, String id){
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getEmail() {return this.email;}
    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }
    public String getId(){return this.id;}


}
