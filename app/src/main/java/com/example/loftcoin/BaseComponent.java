package com.example.loftcoin;

import android.content.Context;

import com.example.loftcoin.data.CoinsRepo;
import com.example.loftcoin.data.CurrencyRepo;
import com.example.loftcoin.data.WalletsRepo;
import com.example.loftcoin.util.ImageLoader;
import com.example.loftcoin.util.Notifier;
import com.example.loftcoin.util.RxSchedulers;

public interface BaseComponent {
    Context context();

    CoinsRepo coinsRepo();

    CurrencyRepo currencyRepo();

    WalletsRepo walletsRepo();

    ImageLoader imageLoader();

    RxSchedulers schedulers();

    Notifier notifier();


}
