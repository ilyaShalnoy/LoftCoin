package com.example.loftcoin.ui.wallets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.BuildConfig;
import com.example.loftcoin.data.Wallet;
import com.example.loftcoin.databinding.LiWalletBinding;
import com.example.loftcoin.util.BalanceFormatter;
import com.example.loftcoin.util.ImageLoader;
import com.example.loftcoin.util.PriceFormatter;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

public class WalletsAdapter extends ListAdapter<Wallet, WalletsAdapter.ViewHolder> {

    private LayoutInflater inflater;

    private final PriceFormatter priceFormatter;

    private final BalanceFormatter balanceFormatter;

    private final ImageLoader imageLoader;

    @Inject
    public WalletsAdapter(PriceFormatter priceFormatter, BalanceFormatter balanceFormatter, ImageLoader imageLoader) {
        super(new DiffUtil.ItemCallback<Wallet>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull Wallet oldItem, @NonNull @NotNull Wallet newItem) {
                return Objects.equals(oldItem.uid(), newItem.uid());
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull Wallet oldItem, @NonNull @NotNull Wallet newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
        this.balanceFormatter = balanceFormatter;
        this.imageLoader = imageLoader;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiWalletBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WalletsAdapter.ViewHolder holder, int position) {
        final Wallet wallet = getItem(position);
        holder.binding.logoCardText.setText(wallet.coin().symbol());
        holder.binding.balance1Text.setText(balanceFormatter.format(wallet));
        final double balance = wallet.balance() * wallet.coin().price();
        holder.binding.balance2Text.setText(priceFormatter.format(wallet.coin().currencyCode(), balance));
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + wallet.coin().id() + ".png")
                .into(holder.binding.logoCard);
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiWalletBinding binding;

        public ViewHolder(@NonNull LiWalletBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setClipToOutline(true);
            this.binding = binding;
        }
    }
}
