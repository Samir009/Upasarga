package com.samir.upasarga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.samir.upasarga.R;
import com.samir.upasarga.helpers.AppActivity;
import com.samir.upasarga.utils.Utilities;

public class SplashScreen extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initializeView();
        initializeListeners();
    }

    @Override
    protected void initializeView() {

    }

    @Override
    protected void initializeListeners() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Utilities.isIntroOpened()){
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, IntroViewPagerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);

    }
}
