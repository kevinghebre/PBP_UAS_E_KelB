package com.kelompok_b.petshop.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelompok_b.petshop.model.User;

import java.util.List;

public class UserResponse {
    @SerializedName("data")
    @Expose
    private User users;

    @SerializedName("message")
    @Expose
    private String message;

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
