package com.example.fastfood.Model;

import java.util.Date;

public class Bill {
    private String id;
    private String table;
    private Date date;
    private int price;
    private boolean thanhtoan;

    public Bill() {
    }

    public Bill(String id, String table, Date date, int price, Boolean thanhtoan) {
        this.id = id;
        this.table = table;
        this.date = date;
        this.price = price;
        this.thanhtoan = thanhtoan;
    }

    public Bill(String table, Date date, int price, Boolean thanhtoan) {
        this.table = table;
        this.date = date;
        this.price = price;
        this.thanhtoan = thanhtoan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getThanhtoan() {
        return thanhtoan;
    }

    public void setThanhtoan(Boolean thanhtoan) {
        this.thanhtoan = thanhtoan;
    }
}
