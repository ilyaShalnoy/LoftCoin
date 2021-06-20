package com.example.loftcoin.ui.rates;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.BuildConfig;
import com.example.loftcoin.R;
import com.example.loftcoin.data.Coin;
import com.example.loftcoin.databinding.LiRatesBinding;
import com.example.loftcoin.util.Formatter;
import com.example.loftcoin.util.ImageLoader;
import com.example.loftcoin.util.PercentFormatter;
import com.example.loftcoin.util.PriceFormatter;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

    private final PriceFormatter priceFormatter;

    private ImageLoader imageLoader;

    private final PercentFormatter percentFormatter;

    private LayoutInflater inflate;

    private int colorNegative = Color.RED;

    private int colorPositive = Color.GREEN;

    @Inject
    protected RatesAdapter(PriceFormatter priceFormatter, PercentFormatter percentFormatter, ImageLoader imageLoader) {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull Coin oldItem, @NonNull @NotNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull Coin oldItem, @NonNull @NotNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Override
            public Object getChangePayload(@NonNull @NotNull Coin oldItem, @NonNull @NotNull Coin newItem) {
                return newItem;
            }
        });

        this.percentFormatter = percentFormatter;
        this.priceFormatter = priceFormatter;
        this.imageLoader = imageLoader;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiRatesBinding.inflate(inflate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RatesAdapter.ViewHolder holder, int position) {
        final Coin coin = getItem(position);
        holder.binding.logoRatesText.setText(coin.symbol());
        holder.binding.price.setText(priceFormatter.format(coin.currencyCode(), coin.price()));
        holder.binding.percent.setText(percentFormatter.format(coin.change24h()));
        if (coin.change24h() > 0) {
            holder.binding.percent.setTextColor(colorPositive);
        } else {
            holder.binding.percent.setTextColor(colorNegative);
        }
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                .into(holder.binding.logoRates);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RatesAdapter.ViewHolder holder, int position, @NonNull @NotNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            final Coin coin = (Coin) payloads.get(0);
            holder.binding.price.setText(priceFormatter.format(coin.currencyCode(), coin.price()));
            holder.binding.percent.setText(percentFormatter.format(coin.change24h()));
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final Context context = recyclerView.getContext();
        inflate = LayoutInflater.from(context);
        TypedValue v = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.percentNegative, v, true);
        colorNegative = v.data;
        context.getTheme().resolveAttribute(R.attr.percentPositive, v, true);
        colorPositive = v.data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiRatesBinding binding;

        public ViewHolder(LiRatesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
