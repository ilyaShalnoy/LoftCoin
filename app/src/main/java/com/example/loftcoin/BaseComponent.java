package com.example.loftcoin;

import android.content.Context;

import com.example.loftcoin.data.CoinsRepo;
import com.example.loftcoin.data.CurrencyRepo;
import com.example.loftcoin.util.ImageLoader;
import com.example.loftcoin.util.RxSchedulers;

public interface BaseComponent {
    Context context();
    CoinsRepo coinsRepo();
    CurrencyRepo currencyRepo();
    ImageLoader imageLoader();
    RxSchedulers schedulers();
}
