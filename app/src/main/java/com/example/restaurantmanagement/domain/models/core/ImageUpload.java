package com.example.restaurantmanagement.domain.models.core;

public class ImageUpload {
    private boolean isUploaded;
    private String image;

    public ImageUpload() {
    }

    public ImageUpload(boolean isUploaded, String image) {
        this.isUploaded = isUploaded;
        this.image = image;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
