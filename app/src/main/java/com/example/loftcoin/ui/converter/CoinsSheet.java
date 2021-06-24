package com.example.loftcoin.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.DialogCurrencyBinding;
import com.example.loftcoin.widget.RxRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CoinsSheet extends BottomSheetDialogFragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final ConverterComponent component;

    private DialogCurrencyBinding binding;

    private ConverterViewModel viewModel;

    static final int MODE_FROM = 1;

    static final int MODE_TO = 2;

    static final String KEY_MODE = "mode";

    private CoinsSheetAdapter adapter;

    private int mode;

    @Inject
    public CoinsSheet(BaseComponent baseComponent) {
        component = DaggerConverterComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireParentFragment(), component.viewModelFactory())
                .get(ConverterViewModel.class);
        adapter = component.coinsSheetAdapter();
        mode = requireArguments().getInt(KEY_MODE, MODE_FROM);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_currency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DialogCurrencyBinding.bind(view);

        binding.recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recycler.setAdapter(adapter);


        disposable.add(viewModel.topCoins().subscribe(adapter::submitList));
        disposable.add(RxRecyclerView.onClick(binding.recycler)
                .map((position) -> adapter.getItem(position))
                .subscribe(coin -> {
                    if (MODE_FROM == mode) {
                        viewModel.fromCoin(coin);
                    } else {
                        viewModel.toCoin(coin);
                    }
                    dismissAllowingStateLoss();
                }));
    }

    @Override
    public void onDestroy() {
        binding.recycler.setAdapter(null);
        disposable.dispose();
        super.onDestroy();

    }
}
