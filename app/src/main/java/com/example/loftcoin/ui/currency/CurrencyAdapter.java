package com.example.loftcoin.ui.currency;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.data.Currency;
import com.example.loftcoin.databinding.LiCurrencyBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CurrencyAdapter extends ListAdapter<Currency, CurrencyAdapter.ViewHolder> {

    private LayoutInflater inflater;

    CurrencyAdapter() {
        super(new DiffUtil.ItemCallback<Currency>() {
            @Override
            public boolean areItemsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
    }

    @Override
    public Currency getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiCurrencyBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final Currency currency = getItem(position);
        holder.binding.name.setText(currency.title());
        holder.binding.symbol.setText(currency.symbol());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiCurrencyBinding binding;

        public ViewHolder(@NonNull LiCurrencyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
