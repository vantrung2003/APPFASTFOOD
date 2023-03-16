package com.example.fastfood.Model;

public class Food {
    private String id;
    private String name;
    private int price;
    private int status;
    private int luotban;

    public Food() {
    }

    public Food(String id, String name, int price, int status, int luotban) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.luotban = luotban;
    }

    public Food(String name, int price, int status, int luotban) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.luotban = luotban;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLuotban() {
        return luotban;
    }

    public void setLuotban(int luotban) {
        this.luotban = luotban;
    }
}
