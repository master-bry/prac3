package com.example.practicle3;

public class Review {
    private long id;
    private String reviewText;
    private String timestamp;

    // Default constructor (optional)
    public Review() {
    }

    // Parameterized constructor
    public Review(long id, String reviewText, String timestamp) {
        this.id = id;
        this.reviewText = reviewText;
        this.timestamp = timestamp;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Setters (optional, if you need to modify the fields later)
    public void setId(long id) {
        this.id = id;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", reviewText='" + reviewText + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}