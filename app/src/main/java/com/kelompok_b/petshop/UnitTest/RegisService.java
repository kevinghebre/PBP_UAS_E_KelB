package com.kelompok_b.petshop.UnitTest;

import com.kelompok_b.petshop.Api.ApiClient;
import com.kelompok_b.petshop.Api.UserApiInterface;
import com.kelompok_b.petshop.Api.UserResponse;
import com.kelompok_b.petshop.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisService {
    public void regis(final RegisView view, String name, String password,String age,
                      String email,String gender,  final RegisCallback callback) {
        UserApiInterface apiService =
                ApiClient.getClient().create(UserApiInterface.class);
        String image = "";
        Call<UserResponse> userCall = apiService.registerUser(name,email,age,gender,image,password);
        userCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call,
                                   Response<UserResponse> response) {
                if (response.body().getMessage().equalsIgnoreCase("berhasil register"
                )) {
                    callback.onSuccess(true, response.body().getUsers());
                } else {
                    callback.onError();
                    view.showRegisError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                view.showErrorResponse(t.getMessage());
                callback.onError();
            }
        });
    }

    public Boolean getValid(final RegisView view,  String name, String password,String age,
                            String email,String gender) {
        final Boolean[] bool = new Boolean[1];
        regis(view, name, password,age, email, gender, new RegisCallback() {
            @Override
            public void onSuccess(boolean value, User user) {
                bool[0] = true;
            }

            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
