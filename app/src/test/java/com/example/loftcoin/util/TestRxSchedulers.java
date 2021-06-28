package com.example.loftcoin.util;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TestRxSchedulers implements RxSchedulers {
    @NonNull
    @NotNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @NotNull
    @Override
    public Scheduler cmp() {
        return Schedulers.trampoline();
    }

    @NonNull
    @NotNull
    @Override
    public Scheduler main() {
        return Schedulers.trampoline();
    }
}
