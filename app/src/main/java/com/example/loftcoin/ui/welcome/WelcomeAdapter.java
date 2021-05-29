package com.example.loftcoin.ui.welcome;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.WelcomePageBinding;

import org.jetbrains.annotations.NotNull;

public class WelcomeAdapter extends RecyclerView.Adapter<WelcomeAdapter.ViewHolder> {

    private static final int[] IMAGES = {
            R.drawable.welcome_page_1,
            R.drawable.welcome_page_2,
            R.drawable.welcome_page_3,
    };

    private static final int[] TITLE = {
            R.string.welcome_page_1_title,
            R.string.welcome_page_2_title,
            R.string.welcome_page_3_title,
    };

    private static final int[] SUBTITLE = {
            R.string.welcome_page_1_subtitle,
            R.string.welcome_page_2_subtitle,
            R.string.welcome_page_3_subtitle,
    };

    private LayoutInflater inflate;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(WelcomePageBinding.inflate(inflate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WelcomeAdapter.ViewHolder holder, int position) {
        holder.binding.image.setImageResource(IMAGES[position]);
        holder.binding.title.setText(TITLE[position]);
        holder.binding.subtitle.setText(SUBTITLE[position]);
    }


    @Override
    public int getItemCount() {
        return IMAGES.length;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflate = LayoutInflater.from(recyclerView.getContext());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final WelcomePageBinding binding;

        public ViewHolder(WelcomePageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

