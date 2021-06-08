package com.example.loftcoin.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.loftcoin.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CurrencyRepoImpl implements CurrencyRepo {


    private Context context;

    public CurrencyRepoImpl(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public LiveData<List<Currency>> availableCurrencies() {
        return new AllCurrenciesLiveData(context);
    }

    @NonNull
    @NotNull
    @Override
    public LiveData<Currency> currency() {
        return null;
    }

    @Override
    public void updateCurrency(@NonNull @NotNull Currency currency) {

    }

    private static class AllCurrenciesLiveData extends  LiveData<List<Currency>> {

        private Context context;

        AllCurrenciesLiveData(Context context){
            this.context = context;
        }

        @Override
        protected void onActive() {
            List<Currency> currencies = new ArrayList<>();
            currencies.add(Currency.create("$", "USD", context.getString(R.string.usd)));
            currencies.add(Currency.create("E", "EUR", context.getString(R.string.eur)));
            currencies.add(Currency.create("R", "RUB", context.getString(R.string.rub)));
            setValue(currencies);
        }
    }

}
