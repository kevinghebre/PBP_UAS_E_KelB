package com.kelompok_b.petshop.model;

import java.io.Serializable;

public class Food implements Serializable {
    private int idFood;
    private String category, food_name, food_image, supplier;
    private double price, calories, net_weight, stock;


    public Food(int idFood, String category, String food_name, String food_image, String supplier, double price, double calories, double net_weight, double stock) {
        this.idFood = idFood;
        this.category = category;
        this.food_name = food_name;
        this.food_image = food_image;
        this.supplier = supplier;
        this.price = price;
        this.calories = calories;
        this.net_weight = net_weight;
        this.stock = stock;
    }

    public Food(String category, String food_name, String food_image, String supplier, double price, double calories, double net_weight, double stock) {
        this.category = category;
        this.food_name = food_name;
        this.food_image = food_image;
        this.supplier = supplier;
        this.price = price;
        this.calories = calories;
        this.net_weight = net_weight;
        this.stock = stock;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_image() {
        return food_image;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public Double getNet_weight() {
        return net_weight;
    }

    public void setNet_weight(double net_weight) {
        this.net_weight = net_weight;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
}
