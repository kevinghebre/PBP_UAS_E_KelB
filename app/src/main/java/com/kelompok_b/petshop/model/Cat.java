package com.kelompok_b.petshop.model;

import java.io.Serializable;

public class Cat implements Serializable {

    public int idCat;
    public String nama_cat, jenis_cat, jk_cat, kategori;
    public String image_cat;
    public Double harga_cat, berat_cat, umur_cat;

    public Cat(int idCat, String nama_cat, String jenis_cat, String jk_cat, String image_cat, String kategori, Double harga_cat, Double berat_cat, Double umur_cat) {
        this.idCat = idCat;
        this.nama_cat = nama_cat;
        this.jenis_cat = jenis_cat;
        this.jk_cat = jk_cat;
        this.image_cat = image_cat;
        this.harga_cat = harga_cat;
        this.berat_cat = berat_cat;
        this.umur_cat = umur_cat;
        this.kategori = "Kucing";
    }

    public Cat(String nama_cat, String jenis_cat, String jk_cat, Double harga_cat, Double berat_cat, Double umur_cat) {
    }

    public Cat(Integer idCat, String nama_cat, String jenis_cat, String jk_cat, String image_cat, Double harga_cat, Double berat_cat, Double umur_cat) {
    }

    public Cat(String kategori, String jenis_cat, Double harga_cat, String nama_cat, Double umur_cat, String jk_cat, Double berat_cat) {

        this.nama_cat = nama_cat;
        this.jenis_cat = jenis_cat;
        this.jk_cat = jk_cat;
        this.harga_cat = harga_cat;
        this.berat_cat = berat_cat;
        this.umur_cat = umur_cat;
        this.kategori = "Kucing";
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNama_cat() {
        return nama_cat;
    }

    public void setNama_cat(String nama_cat) {
        this.nama_cat = nama_cat;
    }

    public String getJenis_cat() {
        return jenis_cat;
    }

    public void setJenis_cat(String jenis_cat) {
        this.jenis_cat = jenis_cat;
    }

    public String getJk_cat() {
        return jk_cat;
    }

    public void setJk_cat(String jk_cat) {
        this.jk_cat = jk_cat;
    }

    public String getImage_cat() {
        return image_cat;
    }

    public void setImage_cat(String image_cat) {
        this.image_cat = image_cat;
    }

    public Double getHarga_cat() {
        return harga_cat;
    }

    public void setHarga_cat(Double harga_cat) {
        this.harga_cat = harga_cat;
    }

    public Double getBerat_cat() {
        return berat_cat;
    }

    public void setBerat_cat(Double berat_cat) {
        this.berat_cat = berat_cat;
    }

    public Double getUmur_cat() {
        return umur_cat;
    }

    public void setUmur_cat(Double umur_cat) {
        this.umur_cat = umur_cat;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}