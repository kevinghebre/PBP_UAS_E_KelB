package com.kelompok_b.petshop.UnitTest;

import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginPresenter {

    private LoginView view;
    private LoginService service;
    private LoginCallback callback;
    TextInputEditText email, password;

    public LoginPresenter(LoginView view, LoginService service) {
        this.view = view;
        this.service = service;

    }

    public void onLoginClicked() {
        if (view.getEmail().isEmpty()) {
            view.showEmailError("Email tidak boleh kosong");
            return;
        } else if (view.getPassword().isEmpty()) {
            view.showPasswordError("Password tidak boleh kosong");
            return;
        }
        else {
            service.login(view, view.getEmail(), view.getPassword(), new
                    LoginCallback() {
                        @Override
                        public void onSuccess(boolean value) {
                            if (view.getEmail().equalsIgnoreCase("wahyuucandra@gmail.com")) {
                                view.startMainActivity();
                            } else {

                                view.startMainActivity();
                            }
                        }

                        @Override
                        public void onError() {
                        }
                    });
            return;
        }
    }
}
