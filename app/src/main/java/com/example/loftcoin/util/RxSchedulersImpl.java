package com.example.loftcoin.util;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Singleton
class RxSchedulersImpl implements RxSchedulers {

    private final Scheduler ioSchedulers;

    @Inject
    RxSchedulersImpl(ExecutorService executor) {
        ioSchedulers = Schedulers.from(executor);
    }

    @NonNull
    @NotNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @NotNull
    @Override
    public Scheduler cmp() {
        return Schedulers.computation();
    }

    @NonNull
    @NotNull
    @Override
    public Scheduler main() {
        return AndroidSchedulers.mainThread();
    }
}
