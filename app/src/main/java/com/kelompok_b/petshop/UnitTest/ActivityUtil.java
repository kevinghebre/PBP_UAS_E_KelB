package com.kelompok_b.petshop.UnitTest;

import android.content.Context;
import android.content.Intent;

import com.kelompok_b.petshop.acc.RegisterActivity;
import com.kelompok_b.petshop.model.User;

public class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startMainActivity() {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    public void startUserProfile(User user) {
        Intent i = new Intent(context, RegisterActivity.class);
        i.putExtra("id",user.getId());
        i.putExtra("name",user.getName());
        i.putExtra("umur",user.getAge());
        i.putExtra("gender",user.getGender());
        i.putExtra("password",user.getPassword());
        context.startActivity(i);
    }
}