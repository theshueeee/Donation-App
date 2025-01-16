package com.example.donorpath;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentId;

public class Ngo {
    private String name;
    private String description;
    private String about;

    private int imageResourceId;
    private String websiteUrl; // New field for NGO's website



    public Ngo(String name, String description, String about,int imageResourceId, String websiteUrl) {
        this.name = name;
        this.description = description;
        this.about = about;
        this.imageResourceId = imageResourceId;
        this.websiteUrl = websiteUrl;


    }

    public Ngo() {
    }

    public String getAbout() {
        return about;
    }


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getWebsiteUrl() {
        return websiteUrl; // Getter for website URL
    }
}



