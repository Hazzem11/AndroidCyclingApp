package com.uottawa.josephsreih.grimpeurscyclingclub;

// Events are created by Club Owners. The only thing a Club Owner
// Does not specify is the eventId (generated automatically). As for the
// EventType, an Event needs to have an EventType. EventTypes can only be created
// By the Admin. Once they're created, a Club Owner can chose which EventType they
// Would like to run their Event under.
public class Event {
    private String name;
    private int totalParticipants;;
    private int maxParticipants;
    private double price;
    private String eventId;
    private Address address;
    private EventType eventType;
    private String date;
    private User eventOrganizer;


    public Event(EventType eventType, String eventId, String nameOfEvent, String eventDate, int eventNumberOfParticipants, double eventPrice, Address eventAddress, User eventOrganizer, int totalParticipants){
        this.eventType = eventType;
        this.eventId = eventId;
        this.name = nameOfEvent;
        this.date = eventDate;
        this.maxParticipants = eventNumberOfParticipants;
        this.price = eventPrice;
        this.address = eventAddress;
        this.eventOrganizer = eventOrganizer;
        this.totalParticipants = totalParticipants;

    }


    public Event(){

    }

    // Setters ----------------------------------------------------
    // Setters are created solely for the instance variables that should be specified by Club Owners
    public void setName(String name){
        this.name = name;
    }

    public void setDate(String date){this.date=date;}
    public void setTotalParticipants(int totalParticipants){
        this.totalParticipants = totalParticipants;
    }

    public void setMaxParticipants(int maxParticipants){
        this.maxParticipants = maxParticipants;
    }

    public void setPrice(double price){
        this.price = price;
    }


    public void setAddress(Address location){
        this.address = location;
    }

    // Getters ----------------------------------------------------
    public String getDate(){return this.date;}
    public String getName(){
        return this.name;
    }

    public int getTotalParticipants(){
        return this.totalParticipants;
    }

    public int getMaxParticipants(){
        return this.maxParticipants;
    }

    public double getPrice(){
        return this.price;
    }

    public Address getAddress(){
        return this.address;
    }


    public EventType getEventType(){
        return this.eventType;
    }

    public String getEventId(){
        return this.eventId;
    }

    public User getEventOrganizer(){return this.eventOrganizer;}

}



