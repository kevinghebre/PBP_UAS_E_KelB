package com.kelompok_b.petshop.UnitTest;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginService {
    FirebaseAuth fAuth;

    public void login(final LoginView view, String email, String password, final LoginCallback callback) {
        fAuth = FirebaseAuth.getInstance();

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    view.showLoginMessage("Log in success!");
                    callback.onSuccess(true);
                } else {
                    view.showLoginError("Log in failed!");
                    callback.onError();
                }
            }
        });
    }

    public Boolean getValid(final LoginView view, String email, String password) {
        final Boolean[] bool = new Boolean[1];

        login(view, email, password, new LoginCallback() {
            @Override
            public void onSuccess(boolean value) {
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