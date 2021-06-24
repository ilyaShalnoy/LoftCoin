package com.example.loftcoin.widget;

import android.support.annotation.NonNull;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.loftcoin.util.OnItemClick;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.android.MainThreadDisposable;

public class RxRecyclerView {

    @NonNull
    public static Observable<Integer> onSnap(@NonNull RecyclerView rv, @NonNull SnapHelper helper) {
        return Observable.create((emitter) -> {
            MainThreadDisposable.verifyMainThread();
            final RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@androidx.annotation.NonNull @NotNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                        final View snapView = helper.findSnapView(rv.getLayoutManager());
                        if (snapView != null) {
                            final RecyclerView.ViewHolder holder = rv.findContainingViewHolder(snapView);
                            if (holder != null) {
                                emitter.onNext(holder.getAdapterPosition());
                            }
                        }
                    }
                }
            };
            emitter.setCancellable(() -> rv.removeOnScrollListener(listener));
            rv.addOnScrollListener(listener);

        });
    }


    public static Observable<Integer> onClick(@NonNull RecyclerView rv) {
        return Observable.create((emitter) -> {
            MainThreadDisposable.verifyMainThread();
            final RecyclerView.OnItemTouchListener listener = new OnItemClick((v) -> {
                final RecyclerView.ViewHolder holder = rv.findContainingViewHolder(v);
                if(holder != null) {
                    emitter.onNext(holder.getAdapterPosition());
                }
            });
            emitter.setCancellable(() -> rv.removeOnItemTouchListener(listener));
            rv.addOnItemTouchListener(listener);
        });
    }

}

