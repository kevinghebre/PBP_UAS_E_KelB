package com.kelompok_b.petshop.model;

import java.io.Serializable;

public class Pet implements Serializable {

    public int idPet;
    public String type_name,pet_name,category,gender;
    public String pet_image;
    public Double price,weight,age;

    public Pet(int idPet, Double age, String type_name, String pet_name, String category, String gender, String pet_image, Double price, Double weight) {
        this.idPet = idPet;
        this.age = age;
        this.type_name = type_name;
        this.pet_name = pet_name;
        this.category = category;
        this.gender = gender;
        this.pet_image = pet_image;
        this.price = price;
        this.weight = weight;
    }

    public Pet(String namaPet, Double age, String jenisHewan, String jenisKelamin, String tipe, Double harga, Double berat) {

    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPet_image() {
        return pet_image;
    }

    public void setPet_image(String pet_image) {
        this.pet_image = pet_image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
