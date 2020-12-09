package com.kelompok_b.petshop.UnitTest;

import com.kelompok_b.petshop.model.User;

public interface RegisView {
    String getName();
    void showNameError(String message);
    String getPassword();
    void showPasswordError(String message);
    String getEmail();
    void showEmailError(String message);
    String getAge();
    void showAgeError(String message);
    String getGender();
    void showGenderError(String message);
    void startMainActivity();
    void startUserProfileActivity(User user);
    void showRegisError(String message);
    void showErrorResponse(String message);
}
