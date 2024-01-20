package com.uottawa.josephsreih.grimpeurscyclingclub;

// Admins create EventTypes that are open to Club Owners to chose
// And host. EventTypes and their description are filled out by admins, while
// Club Owners can go into detail about this event once the EventType is chosen.
public class EventType {

    private String id;
    private String typeOfEvent;
    private String description;

    public EventType(){
        // for firebase
    }
    public EventType(String typeOfEvent, String description, String id){
        this.typeOfEvent = typeOfEvent;
        this.description = description;
        this.id = id;
    }
    public String getTypeOfEvent() {
        return typeOfEvent;
    }

    public String getDescription() {
        return this.description;
    }

    public String getId(){
        return this.id;
    }

    public boolean isEmpty() {
        return (typeOfEvent == null);
    }


}
