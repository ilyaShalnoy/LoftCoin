package com.example.loftcoin.ui.rates;

import androidx.annotation.NonNull;

import com.example.loftcoin.data.Coin;

import java.util.List;

public interface RatesView {

    void showCoins(@NonNull List<? extends Coin>);

    void showError(@NonNull String error);

}
