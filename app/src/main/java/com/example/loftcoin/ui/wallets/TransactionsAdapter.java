package com.example.loftcoin.ui.wallets;

import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.TransitionRes;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.data.Transaction;
import com.example.loftcoin.data.Wallet;
import com.example.loftcoin.databinding.LiTransactionBinding;
import com.example.loftcoin.util.PriceFormatter;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

class TransactionsAdapter extends ListAdapter<Transaction, TransactionsAdapter.ViewHolder> {

    private LayoutInflater inflater;

    private final PriceFormatter priceFormatter;

    private int colorNegative = Color.RED;

    private int colorPositive = Color.GREEN;


    @Inject
    public TransactionsAdapter(PriceFormatter priceFormatter) {
        super(new DiffUtil.ItemCallback<Transaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull Transaction oldItem, @NonNull @NotNull Transaction newItem) {
                return Objects.equals(oldItem.uid(), newItem.uid());
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull Transaction oldItem, @NonNull @NotNull Transaction newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiTransactionBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final Transaction transaction = getItem(position);
        holder.binding.amount1.setText(priceFormatter.format(transaction.amount()));
        final double flatAmount = transaction.amount() * transaction.coin().price();
        if (transaction.amount() > 0) {
            holder.binding.amount2.setTextColor(colorPositive);
        } else {
            holder.binding.amount2.setTextColor(colorNegative);
        }
        holder.binding.amount2.setText(priceFormatter.format(transaction.coin().currencyCode(), flatAmount));
        holder.binding.timestamp.setText(DateFormat.getDateFormat(inflater.getContext()).format(transaction.createdAt()));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiTransactionBinding binding;

        public ViewHolder(@NonNull LiTransactionBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setClipToOutline(true);
            this.binding = binding;
        }
    }
}
