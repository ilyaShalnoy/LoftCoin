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
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


@Singleton
class CurrencyRepoImpl implements CurrencyRepo {


    private static final String KEY_CURRENCY = "currency";

    private final Map<String, Currency> availableCurrencies = new HashMap<>();

    private SharedPreferences preferences;

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
    public Observable<Currency> currency() {
        return Observable.create(emitter -> {
            SharedPreferences.OnSharedPreferenceChangeListener listener = (preferences, key) -> {
                if (!emitter.isDisposed() && KEY_CURRENCY.equals(key)) {
                    emitter.onNext(Objects.requireNonNull(availableCurrencies.get(preferences.getString(key, "USD"))));
                }
            };
            preferences.registerOnSharedPreferenceChangeListener(listener);
            emitter.setCancellable(() -> preferences.unregisterOnSharedPreferenceChangeListener(listener));
            emitter.onNext(Objects.requireNonNull(availableCurrencies.get(preferences.getString(KEY_CURRENCY, "USD"))));
        });
    }

    @Override
    public void updateCurrency(@NonNull @NotNull Currency currency) {
        preferences.edit().putString(KEY_CURRENCY, currency.code()).apply();
    }

}
