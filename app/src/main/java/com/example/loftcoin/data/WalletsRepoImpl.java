package com.example.loftcoin.data;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class WalletsRepoImpl implements WalletsRepo {

    private final FirebaseFirestore firestore;
    private CoinsRepo coinsRepo;

    @Inject
    WalletsRepoImpl(CoinsRepo coinsRepo) {
        this.firestore = FirebaseFirestore.getInstance();
        this.coinsRepo = coinsRepo;

    }

    @NonNull
    @NotNull
    @Override
    public Observable<List<Wallet>> wallets(@NonNull @NotNull Currency currency) {
        return Observable
                .<QuerySnapshot>create(emitter -> {
                    final ListenerRegistration registration = firestore.collection("wallets")
                            .addSnapshotListener((snapshots, error) -> {
                                if (emitter.isDisposed()) return;
                                if (snapshots != null) {
                                    emitter.onNext(snapshots);
                                } else if (error != null) {
                                    emitter.tryOnError(error);
                                }
                            });
                    emitter.setCancellable(registration::remove);
                })
                .map(QuerySnapshot::getDocuments)
                .switchMapSingle((documents) -> Observable
                            .fromIterable(documents)
                            .switchMapSingle((document) -> coinsRepo
                                    .coin(currency, Objects.requireNonNull(document.
                                            getLong("coinId"), "coinId"))
                                    .map((coin) -> Wallet.create(
                                            document.getId(),
                                            coin,
                                            document.getDouble("balance")
                                    ))
                            )
                            .toList()
                );
    }


    @NonNull
    @NotNull
    @Override
    public Observable<List<Transaction>> transaction(@NonNull @NotNull Wallet wallet) {
        return Observable.empty();
    }
}
