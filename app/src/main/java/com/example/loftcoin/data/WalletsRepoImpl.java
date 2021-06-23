package com.example.loftcoin.data;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
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
    @Override
    public Observable<List<Wallet>> wallets(@NonNull Currency currency) {
        return Observable
                .<QuerySnapshot>create(emitter -> {
                    final ListenerRegistration registration = firestore
                            .collection("wallets")
                            .orderBy("created_at", Query.Direction.ASCENDING)
                            .addSnapshotListener((snapshots, e) -> {
                                if (emitter.isDisposed()) return;
                                if (snapshots != null) {
                                    emitter.onNext(snapshots);
                                } else if (e != null) {
                                    emitter.tryOnError(e);
                                }
                            });
                    emitter.setCancellable(registration::remove);
                })
                .map(QuerySnapshot::getDocuments)
                .switchMapSingle((documents) -> Observable
                        .fromIterable(documents)
                        .flatMapSingle((document) -> coinsRepo
                                .coin(currency, Objects.requireNonNull(document
                                        .getLong("coinId"), "coinId"))
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
    @Override
    public Observable<List<Transaction>> transactions(@NonNull Wallet wallet) {
        return Observable
                .<QuerySnapshot>create(emitter -> {
                    final ListenerRegistration registration = firestore
                            .collection("wallets")
                            .document(wallet.uid())
                            .collection("transactions")
                            .addSnapshotListener((snapshots, e) -> {
                                if (emitter.isDisposed()) return;
                                if (snapshots != null) {
                                    emitter.onNext(snapshots);
                                } else if (e != null) {
                                    emitter.tryOnError(e);
                                }
                            });
                    emitter.setCancellable(registration::remove);
                })
                .map(QuerySnapshot::getDocuments)
                .switchMapSingle((documents) -> Observable
                        .fromIterable(documents)
                        .map((document) -> Transaction.create(
                                document.getId(),
                                wallet.coin(),
                                document.getDouble("amount"),
                                document.getDate("at")
                        ))
                        .toList()
                );
    }
}
