package com.example.donorpath;

public class tips {
    private String username;

    private String tips;

    public tips() {

    }

    public tips(String username, String tips) {
        this.username = username;
        this.tips = tips;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

