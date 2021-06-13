package com.example.loftcoin.util;

import android.os.Build;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;

public class PriceFormatter implements Formatter<Double> {

    @NonNull
    @NotNull
    @Override
    public String format(@NonNull @NotNull Double value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return android.icu.text.NumberFormat.getCurrencyInstance().format(value);
        } else {
            return java.text.NumberFormat.getCurrencyInstance().format(value);
        }
    }
}
