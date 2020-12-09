package com.kelompok_b.petshop.UnitTest;

public interface LoginView {

    String getEmail();

    void showEmailError(String message);

    String getPassword();

    void showPasswordError(String message);

    void startMainActivity();

    void showLoginError(String message);

    void showLoginMessage(String message);
}
