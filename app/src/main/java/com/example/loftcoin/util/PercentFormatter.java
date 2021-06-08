package com.example.loftcoin.util;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class PercentFormatter implements Formatter<Double> {

    public PercentFormatter(){

    }
    @NonNull
    @NotNull
    @Override
    public String format(@NonNull @NotNull Double value) {
        return String.format(Locale.US ,"%.2f%%", value);
    }
}
