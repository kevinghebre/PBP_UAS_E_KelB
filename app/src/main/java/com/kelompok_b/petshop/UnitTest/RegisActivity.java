package com.kelompok_b.petshop.UnitTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.kelompok_b.petshop.R;
import com.kelompok_b.petshop.model.User;

public class RegisActivity extends AppCompatActivity implements RegisView {
    private MaterialButton btnRegis;
    private TextInputEditText name, age, gender, email, password;
    private RegisPresenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegis = findViewById(R.id.btn_register);
        name = findViewById(R.id.input_name_register);
        age = findViewById(R.id.input_age_register);
        gender = findViewById(R.id.input_gender_register);
        email = findViewById(R.id.input_email_register);
        password = findViewById(R.id.input_password_register);
        presenter = new RegisPresenter(this, new RegisService());
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRegisClicked();
            }
        });
    }
    @Override
    public String getName() {
        return name.getText().toString();
    }
    @Override
    public String getAge() {
        return age.getText().toString();
    }
    @Override
    public String getGender() {
        return gender.getText().toString();
    }
    @Override
    public String getEmail() {
        return email.getText().toString();
    }
    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showNameError(String message) {
        name.setError(message);
    }

    @Override
    public void showAgeError(String message) {
        age.setError(message);
    }

    @Override
    public void showGenderError(String message) {
        gender.setError(message);
    }

    @Override
    public void showEmailError(String message) {
        email.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        password.setError(message);
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }
    @Override
    public void startUserProfileActivity(User user) {
        new ActivityUtil(this).startUserProfile(user);
    }
    @Override
    public void showRegisError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
