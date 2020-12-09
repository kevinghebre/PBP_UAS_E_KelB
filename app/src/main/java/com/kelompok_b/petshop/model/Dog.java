package com.kelompok_b.petshop.model;

import java.io.Serializable;

public class Dog implements Serializable {

    public int idDog;
    public String nama_dog, jenis_dog, jk_dog, kategori;
    public String image_dog;
    public Double harga_dog, berat_dog, umur_dog;

    public Dog(int idDog, String nama_dog, String jenis_dog, String jk_dog, String kategori, String image_dog, Double harga_dog, Double berat_dog, Double umur_dog) {
        this.idDog = idDog;
        this.nama_dog = nama_dog;
        this.jenis_dog = jenis_dog;
        this.jk_dog = jk_dog;
        this.kategori = kategori;
        this.image_dog = image_dog;
        this.harga_dog = harga_dog;
        this.berat_dog = berat_dog;
        this.umur_dog = umur_dog;
    }

    public Dog(String nama_dog, String jenis_dog, String jk_dog, Double harga_dog, Double berat_dog, Double umur_dog) {
        this.nama_dog = nama_dog;
        this.jenis_dog = jenis_dog;
        this.jk_dog = jk_dog;
        this.harga_dog = harga_dog;
        this.berat_dog = berat_dog;
        this.umur_dog = umur_dog;
    }

    public Dog(String kategori, String jenis_dog, Double harga_dog, String nama_dog, Double umur_dog, String jk_dog, Double berat_dog) {
        this.nama_dog = nama_dog;
        this.jenis_dog = jenis_dog;
        this.jk_dog = jk_dog;
        this.kategori = kategori;
        this.harga_dog = harga_dog;
        this.berat_dog = berat_dog;
        this.umur_dog = umur_dog;
    }

    public int getIdDog() {
        return idDog;
    }

    public void setIdDog(int idDog) {
        this.idDog = idDog;
    }

    public String getNama_dog() {
        return nama_dog;
    }

    public void setNama_dog(String nama_dog) {
        this.nama_dog = nama_dog;
    }

    public String getJenis_dog() {
        return jenis_dog;
    }

    public void setJenis_dog(String jenis_dog) {
        this.jenis_dog = jenis_dog;
    }

    public String getJk_dog() {
        return jk_dog;
    }

    public void setJk_dog(String jk_dog) {
        this.jk_dog = jk_dog;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getImage_dog() {
        return image_dog;
    }

    public void setImage_dog(String image_dog) {
        this.image_dog = image_dog;
    }

    public Double getHarga_dog() {
        return harga_dog;
    }

    public void setHarga_dog(Double harga_dog) {
        this.harga_dog = harga_dog;
    }

    public Double getBerat_dog() {
        return berat_dog;
    }

    public void setBerat_dog(Double berat_dog) {
        this.berat_dog = berat_dog;
    }

    public Double getUmur_dog() {
        return umur_dog;
    }

    public void setUmur_dog(Double umur_dog) {
        this.umur_dog = umur_dog;
    }
}
