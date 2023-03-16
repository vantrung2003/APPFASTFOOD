package com.example.fastfood.Model;

public class BillFood {
    private String id;
    private String idBill;
    private String idFood;
    private int figure;
    private int price;
    private boolean check;

    public BillFood() {
    }

    public BillFood(String id, String idBill, String idFood, int figure, int price, Boolean check) {
        this.id = id;
        this.idBill = idBill;
        this.idFood = idFood;
        this.figure = figure;
        this.price = price;
        this.check = check;
    }

    public BillFood(String idBill, String idFood, int figure, int price, Boolean check) {
        this.idBill = idBill;
        this.idFood = idFood;
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

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
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
