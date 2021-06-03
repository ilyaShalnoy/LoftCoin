package com.example.loftcoin.ui.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentRatesBinding;

import org.jetbrains.annotations.NotNull;

public class RatesFragment extends Fragment implements RatesView {

    private FragmentRatesBinding binding;

    private RatesPresenter presenter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RatesPresenter();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRatesBinding.bind(view);
        presenter.attach(this);
    }

    @Override
    public void onDestroyView() {
        presenter.detach(this);
        super.onDestroyView();
    }

    @Override
    public void showCoins() {

    }

    @Override
    public void showError(@NonNull @NotNull String error) {

    }
}
