package com.example.loftcoin.data;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class TestCoinsRepo implements CoinsRepo{

    public final Subject<List<Coin>> listings = PublishSubject.create();

    public Query lastListingsQuery;

    @NonNull
    @NotNull
    @Override
    public Observable<List<Coin>> listings(@NonNull @NotNull CoinsRepo.Query query) {
        lastListingsQuery = query;
        return listings;

    }

    @NonNull
    @NotNull
    @Override
    public Single<Coin> coin(Currency currency, long id) {
        return Single.error(() -> new AssertionError("Stub!"));
    }

    @NonNull
    @NotNull
    @Override
    public Single<Coin> nextPopularCoin(@NonNull @NotNull Currency currency, List<Integer> ids) {
        return Single.error(() -> new AssertionError("Stub!"));
    }

    @NonNull
    @NotNull
    @Override
    public Observable<List<Coin>> topCoins(@NonNull @NotNull Currency currency) {
        return Observable.error(() -> new AssertionError("Stub!"));
    }
}
