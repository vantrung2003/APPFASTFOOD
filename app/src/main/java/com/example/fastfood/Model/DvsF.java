package com.example.fastfood.Model;

public class DvsF {
    private String id;
    private String idBill;
    private String idDrinkorFood;
    private int figure;
    private int price;
    private boolean check;
    private boolean drinkorfood;

    public DvsF(String id, String idBill, String idDrinkorFood, int figure, int price, boolean check, boolean drinkorfood) {
        this.id = id;
        this.idBill = idBill;
        this.idDrinkorFood = idDrinkorFood;
        this.figure = figure;
        this.price = price;
        this.check = check;
        this.drinkorfood = drinkorfood;
    }

    public DvsF() {
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

    public String idDrinkorFood() {
        return idDrinkorFood;
    }

    public void setidDrinkorFood(String idDrinkorFood) {
        this.idDrinkorFood = idDrinkorFood;
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

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isDrinkorfood() {
        return drinkorfood;
    }

    public void setDrinkorfood(boolean drinkorfood) {
        this.drinkorfood = drinkorfood;
    }
}
