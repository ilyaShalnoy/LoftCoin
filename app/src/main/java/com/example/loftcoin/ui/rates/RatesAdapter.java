package com.example.loftcoin.ui.rates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.data.Coin;
import com.example.loftcoin.databinding.LiRatesBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> {

    private LayoutInflater inflater;

    private final List<? extends Coin> coins;

    public RatesAdapter(List<? extends Coin> coins) {
        setHasStableIds(true);
        this.coins = coins;
    }

    @Override
    public long getItemId(int position) {
        return coins.get(position).id();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiRatesBinding.inflate(inflater, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RatesAdapter.ViewHolder holder, int position) {
        holder.bind(coins.get(position));
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiRatesBinding binding;

        public ViewHolder(LiRatesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Coin coin){
            binding.logoRatesText.setText(coin.symbol());
        }
    }
}
