package com.kelompok_b.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuHeader extends AppCompatActivity {
    String nameUser, emailUser;
    TextView name, email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);

        email = findViewById(R.id.email_header_user);
        name = findViewById(R.id.nameUser);

        Intent i = getIntent();
        nameUser = i.getStringExtra("name");
        emailUser = i.getStringExtra("email");

        email.setText(nameUser);
        name.setText(emailUser);
    }
}
