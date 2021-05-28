package com.example.loftcoin.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loftcoin.R;
import com.example.loftcoin.ui.main.MainActivity;
import com.example.loftcoin.ui.welcome.WelcomeActivity;

public class SplashActivity extends AppCompatActivity {

    private final Handler handler = new Handler();

    private Runnable goNext;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(WelcomeActivity.KEY_SHOW_WELCOME, true)) {
            goNext = () -> startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
        } else {
            goNext =() -> startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        handler.postDelayed(goNext, 1500);
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(goNext);
        super.onStop();
    }
}

