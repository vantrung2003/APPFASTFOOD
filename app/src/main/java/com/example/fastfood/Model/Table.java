package com.example.fastfood.Model;

public class Table {
    private String id;
    private String name;
    private boolean dachon;

    public Table() {
    }

    public Table(String id, String name, boolean dachon) {
        this.id = id;
        this.name = name;
        this.dachon = dachon;
    }

    public Table(String name, boolean dachon) {
        this.name = name;
        this.dachon = dachon;
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

    public boolean isDachon() {
        return dachon;
    }

    public void setDachon(boolean dachon) {
        this.dachon = dachon;
    }
}
