package com.kelompok_b.petshop.UnitTest;

import com.kelompok_b.petshop.model.User;

public class RegisPresenter {
    private RegisView view;
    private RegisService service;
    private RegisCallback callback;

    public RegisPresenter(RegisView view, RegisService service) {
        this.view = view;
        this.service = service;
    }

    public void onRegisClicked() {
        if (view.getName().isEmpty()) {
            view.showNameError("Nama tidak boleh kosong");
            return;
        } else if (view.getAge().isEmpty()) {
            view.showAgeError("Umur tidak boleh kosong");
            return;
        } else if (view.getEmail().isEmpty()) {
        view.showEmailError("Email tidak boleh kosong");
        return;
        } else if (view.getGender().isEmpty()) {
            view.showGenderError("Jenis Kelamin tidak boleh kosong");
            return;
        } else if (view.getPassword().isEmpty()) {
            view.showPasswordError("Password tidak boleh kosong");
            return;
        }
        else {
            service.regis(view, view.getName(), view.getPassword(),view.getGender(),view.getEmail(),view.getAge(), new
                    RegisCallback() {
                        @Override
                        public void onSuccess(boolean value, User user) {
                            if (user.getName().equalsIgnoreCase("kaleb")) {
                                view.startMainActivity();
                            } else {
                                view.startUserProfileActivity(user);
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

