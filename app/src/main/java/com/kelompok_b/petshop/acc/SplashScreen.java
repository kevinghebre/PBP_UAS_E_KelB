package com.kelompok_b.petshop.acc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.kelompok_b.petshop.R;

public class SplashScreen extends AppCompatActivity {

//    //gambar
//    View logo_p,logo_e,logo_t,logo_shop;
//
//    //Animation
//    Animation animation_p,animation_e,animation_t, animation_shop;
    SharedPreferences sharedPreferences;
//     Pindah Main 4000 = 4s
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        sharedPreferences = getSharedPreferences("night",0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode",true);
        if (booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
//
//
//        // Animation Utils
//        animation_p = AnimationUtils.loadAnimation(this,R.anim.pet_animation_p);
//        animation_e = AnimationUtils.loadAnimation(this,R.anim.pet_animation_e);
//        animation_t = AnimationUtils.loadAnimation(this,R.anim.pet_animation_t);
//        animation_shop = AnimationUtils.loadAnimation(this,R.anim.pet_animation_shop);
//
//
//        //FindById untuk gambar
//        logo_p = findViewById(R.id.pet_logo_p);
//        logo_e = findViewById(R.id.pet_logo_e);
//        logo_t = findViewById(R.id.pet_logo_t);
//        logo_shop = findViewById(R.id.pet_logo_shop);
//
//        // Set Animasi Untuk gambar
//        logo_p.setAnimation(animation_p);
//        logo_e.setAnimation(animation_e);
//        logo_t.setAnimation(animation_t);
//        logo_shop.setAnimation(animation_shop);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this, LoginActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
