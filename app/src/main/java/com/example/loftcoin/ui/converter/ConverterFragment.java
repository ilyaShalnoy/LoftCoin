package com.example.loftcoin.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentConverterBinding;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ConverterFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final ConverterComponent component;

    private ConverterViewModel viewModel;

    private FragmentConverterBinding binding;


    @Inject
    public ConverterFragment(BaseComponent baseComponent) {
        component = DaggerConverterComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireParentFragment(), component.viewModelFactory())
                .get(ConverterViewModel.class);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentConverterBinding.bind(view);

        disposable.add(viewModel.topCoins().subscribe());


        final NavController navController = NavHostFragment.findNavController(this);
        disposable.add(RxView.clicks(binding.fromCoin)
                .subscribe(v -> {
                    final Bundle args = new Bundle();
                    args.putInt(CoinsSheet.KEY_MODE, CoinsSheet.MODE_FROM);
                    navController.navigate(R.id.coins_sheet, args);
                }));

        disposable.add(RxView.clicks(binding.toCoin)
                .subscribe(v -> {
                    final Bundle args = new Bundle();
                    args.putInt(CoinsSheet.KEY_MODE, CoinsSheet.MODE_TO);
                    navController.navigate(R.id.coins_sheet, args);
                }));

        disposable.add(viewModel.fromCoin().subscribe(coin -> {
            binding.fromCoin.setText(coin.symbol());
        }));
        disposable.add(viewModel.toCoin().subscribe(coin -> {
            binding.toCoin.setText(coin.symbol());
        }));

        disposable.add(RxTextView.textChanges(binding.from).subscribe(viewModel::fromValue));
        disposable.add(RxTextView.textChanges(binding.to).subscribe(viewModel::toValue));

        disposable.add(viewModel.fromValue()
                .distinctUntilChanged()
                .subscribe(text -> {
                    binding.from.setText(text);
                    binding.from.setSelection(text.length());
                }));
        disposable.add(viewModel.toValue()
                .distinctUntilChanged()
                .subscribe(text -> {
                    binding.to.setText(text);
                    binding.to.setSelection(text.length());
                }));
    }

    @Override
    public void onDestroyView() {
        disposable.clear();
        super.onDestroyView();
    }

}
