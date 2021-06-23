package com.example.loftcoin.ui.wallets;

import android.support.annotation.NonNull;

import androidx.lifecycle.ViewModel;

import com.example.loftcoin.data.CurrencyRepo;
import com.example.loftcoin.data.Transaction;
import com.example.loftcoin.data.Wallet;
import com.example.loftcoin.data.WalletsRepo;
import com.example.loftcoin.util.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class WalletsViewModel extends ViewModel {

    private final Subject<Integer> walletPosition = BehaviorSubject.createDefault(0);

    private final Observable<List<Wallet>> wallets;

    private final Observable<List<Transaction>> transactions;

    private final RxSchedulers schedulers;

    private final WalletsRepo walletsRepo;

    private final CurrencyRepo currencyRepo;


    @Inject
    WalletsViewModel(WalletsRepo walletsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {

        this.schedulers = schedulers;
        this.walletsRepo = walletsRepo;
        this.currencyRepo = currencyRepo;

        wallets = currencyRepo.currency()
                .switchMap(walletsRepo::wallets)
                .doOnNext((wal) -> Timber.d("%s", wal))
                .replay(1)
                .autoConnect();

        transactions = wallets
                .switchMap((wallets) -> walletPosition
                        .map(wallets::get)
                )
                .switchMap(walletsRepo::transactions)
                .doOnNext((t) -> Timber.d("%s", t))
                .replay(1)
                .autoConnect();
    }


    @NonNull
    Observable<List<Wallet>> wallets() {
        return wallets.observeOn(schedulers.main());
    }

    @NonNull
    Observable<List<Transaction>> transaction() {
        return transactions.observeOn(schedulers.main());
    }
//
//    @NonNull
//    Completable addWallet() {
//        return Completable.fromAction(() -> Timber.d("-"));
//    }
}

