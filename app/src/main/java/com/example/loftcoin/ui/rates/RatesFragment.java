package com.example.loftcoin.ui.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentRatesBinding;

import org.jetbrains.annotations.NotNull;


public class RatesFragment extends Fragment {

    private FragmentRatesBinding binding;

    private RatesAdapter adapter;

    private RatesViewModel viewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RatesViewModel.class);
        adapter = new RatesAdapter();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRatesBinding.bind(view);
        binding.recyclerRates.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recyclerRates.swapAdapter(adapter,false);
        binding.recyclerRates.setHasFixedSize(true);
        viewModel.coins().observe(getViewLifecycleOwner(), (coins) -> {
            adapter.submitList(coins);
        });
    }

    @Override
    public void onDestroyView() {
        binding.recyclerRates.swapAdapter(null, false);
        super.onDestroyView();
    }
}
