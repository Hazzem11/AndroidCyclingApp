package com.uottawa.josephsreih.grimpeurscyclingclub;

public class Rating {

    private float rating;
    private String comment;

    public Rating() {
        // Default constructor required for Firebase
    }

    public Rating(float rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
