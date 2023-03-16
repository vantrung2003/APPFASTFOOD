package com.example.fastfood.Model;

import java.util.Date;

public class Baskets {

    private String id;
    private String table;
    private Date date;

    public Baskets() {
    }

    public Baskets(String id, String table, Date date) {
        this.id = id;
        this.table = table;
        this.date = date;
    }

    public Baskets(String table, Date date) {
        this.table = table;
        this.date = date;
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
}
