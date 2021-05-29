package com.example.loftcoin.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());

    }
}