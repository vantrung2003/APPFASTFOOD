package com.example.fastfood.Model;

public class BillDrink {
    private String id;
    private String idBill;
    private String idDrink;
    private int figure;
    private int price;
    private boolean check;

    public BillDrink() {
    }

    public BillDrink(String id, String idBill, String idDrink, int figure, int price, Boolean check) {
        this.id = id;
        this.idBill = idBill;
        this.idDrink = idDrink;
        this.figure = figure;
        this.price = price;
        this.check = check;
    }

    public BillDrink(String idBill, String idDrink, int figure, int price, Boolean check) {
        this.idBill = idBill;
        this.idDrink = idDrink;
        this.figure = figure;
        this.price = price;
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
