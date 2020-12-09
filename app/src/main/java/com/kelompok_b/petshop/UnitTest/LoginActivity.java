package com.kelompok_b.petshop.UnitTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kelompok_b.petshop.R;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private MaterialButton btnLogin;
    private TextInputEditText email, password;
    TextInputLayout emailLayout, passwordLayout;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_sign_in);
        email = findViewById(R.id.input_email_login);
        password = findViewById(R.id.input_password_login);
        emailLayout = findViewById(R.id.input_email_login_layout);
        passwordLayout = findViewById(R.id.input_password_login_layout);

        presenter = new LoginPresenter(this, new LoginService());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLoginClicked();
            }
        });
    }

    @Override
    public String getEmail() {
        return email.getText().toString();
    }

    @Override
    public void showEmailError(String message) {
        emailLayout.setError(message);
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showPasswordError(String message) {
        passwordLayout.setError(message);
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    @Override
    public void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
