package com.kelompok_b.petshop.UnitTest;

import com.kelompok_b.petshop.model.User;

public interface RegisCallback {
    void onSuccess(boolean value, User user);
    void onError();
}
