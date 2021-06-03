package com.example.loftcoin.ui.rates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.data.Coin;
import com.example.loftcoin.databinding.LiRatesBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

    private LayoutInflater inflate;

    protected RatesAdapter() {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull Coin oldItem, @NonNull @NotNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull Coin oldItem, @NonNull @NotNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiRatesBinding.inflate(inflate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RatesAdapter.ViewHolder holder, int position) {
        holder.binding.logoRatesText.setText(getItem(position).symbol());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflate = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiRatesBinding binding;

        public ViewHolder(LiRatesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

}
