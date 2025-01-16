package com.example.donorpath;


public class LearnClass {
    private final String title;
    private final String subtitle;
    private final int imageResId;
    private final String buttonText;
    private final String url;



    public LearnClass(String title, String subtitle, int imageResId, String buttonText, String url) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageResId = imageResId;
        this.buttonText = buttonText;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getUrl() {
        return url;
    }
}

