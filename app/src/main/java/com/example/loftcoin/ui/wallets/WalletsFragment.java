package com.example.loftcoin.ui.wallets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentWalletsBinding;

import org.jetbrains.annotations.NotNull;

public class WalletsFragment extends Fragment {

    private SnapHelper walletsSnapHelper;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentWalletsBinding binding = FragmentWalletsBinding.bind(view); //TODO: ?
        walletsSnapHelper = new PagerSnapHelper();
        walletsSnapHelper.attachToRecyclerView(binding.recycler);
        binding.recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        binding.recycler.setAdapter(new WalletsAdapter());
        binding.recycler.setVisibility(View.VISIBLE);
        binding.addWalletCard.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        walletsSnapHelper.attachToRecyclerView(null);
        super.onDestroyView();
    }
}
