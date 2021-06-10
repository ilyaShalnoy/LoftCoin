package com.example.loftcoin.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import com.example.loftcoin.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
class CurrencyRepoImpl implements CurrencyRepo {


    private static final String KEY_CURRENCY = "currency";

    private final Map<String, Currency> availableCurrencies = new HashMap<>();

    private SharedPreferences preferences;

    private Context context;

    @Inject
    CurrencyRepoImpl(@NonNull Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        availableCurrencies.put("USD", Currency.create("$", "USD", context.getString(R.string.usd)));
        availableCurrencies.put("EUR", Currency.create("E", "EUR", context.getString(R.string.eur)));
        availableCurrencies.put("RUB", Currency.create("R", "RUB", context.getString(R.string.rub)));
    }

    @NonNull
    @NotNull
    @Override
    public LiveData<List<Currency>> availableCurrencies() {
        final MutableLiveData<List<Currency>> liveData = new MutableLiveData<>();
        liveData.setValue(new ArrayList<>(availableCurrencies.values()));
        return liveData;
    }

    @NonNull
    @NotNull
    @Override
    public LiveData<Currency> currency() {
        return new CurrencyLiveDate();
    }

    @Override
    public void updateCurrency(@NonNull @NotNull Currency currency) {
        preferences.edit().putString(KEY_CURRENCY, currency.code()).apply();
    }

    private class CurrencyLiveDate extends LiveData<Currency> implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        protected void onActive() {
            preferences.registerOnSharedPreferenceChangeListener(this);
            setValue(availableCurrencies.get(preferences.getString(KEY_CURRENCY, "USD")));
        }

        @Override
        protected void onInactive() {
            preferences.unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            setValue(availableCurrencies.get(preferences.getString(key, "USD")));
        }
    }

}
