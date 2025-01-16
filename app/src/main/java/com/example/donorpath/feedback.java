package com.example.donorpath;

public class feedback {
    private String userId;
    private String ngoName;
    private float rating;
    private String feedback;

    public feedback() {
    }

    public feedback(String userId, String ngoName, float rating, String feedback) {
        this.userId = userId;
        this.ngoName = ngoName;
        this.rating = rating;
        this.feedback = feedback;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

