package com.example.loftcoin.util;

import androidx.annotation.NonNull;

import com.example.loftcoin.data.Wallet;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BalanceFormatter implements Formatter<Wallet> {

    @Inject
    public BalanceFormatter() {
    }

    @NonNull
    @NotNull
    @Override
    public String format(@NonNull @NotNull Wallet value) {
        final DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        final DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(value.coin().symbol());
        format.setDecimalFormatSymbols(symbols);
        return format.format(value.balance());
    }
}
