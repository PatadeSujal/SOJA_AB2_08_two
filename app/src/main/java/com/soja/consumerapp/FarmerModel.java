package com.soja.consumerapp;

public class FarmerModel {
    private String name;
    private String profileImage;

    public FarmerModel(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    public String getName() { return name; }
    public String getProfileImage() { return profileImage;}
}

