package com.uottawa.josephsreih.grimpeurscyclingclub;

public class Profile {
    private String phoneNumber;
    private String socialMediaLink;
    private String name;
    private User user;

    // Constructor
    public Profile(String phoneNumber, String socialMediaLink, String name, User user) {
        this.phoneNumber = phoneNumber;
        this.socialMediaLink = socialMediaLink;
        this.name = name;
        this.user = user;
    }
    public Profile(){

    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for socialMediaLink
    public String getSocialMediaLink() {
        return socialMediaLink;
    }

    public void setSocialMediaLink(String socialMediaLink) {
        this.socialMediaLink = socialMediaLink;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
}
