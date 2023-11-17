package com.prakhar.fooddboy;

public class CartItem {

    private String id;
    private String dishName;
    private int quantity;
    private String price;

    public CartItem() {
        // Default constructor required for Firebase Realtime Database
    }

    public CartItem(String id, String dishName, int quantity, String price) {
        this.id = id;
        this.dishName = dishName;
        this.quantity = quantity;
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}