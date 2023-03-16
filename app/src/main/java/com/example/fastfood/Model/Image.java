package com.example.fastfood.Model;

public class Image {

    private String idfood;
    private String id;

    public Image() {
    }

    public Image(String idfood) {
        this.idfood = idfood;
    }

    public Image(String id, String idfood) {
        this.idfood = idfood;
        this.id = id;
    }

    public String getIdfood() {
        return idfood;
    }

    public void setIdfood(String idfood) {
        this.idfood = idfood;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
