package com.example.loftcoin.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;


@Entity
@AutoValue
abstract class RoomCoin implements Coin {

    static RoomCoin create(String name, String symbol, double change24h, int rank, double price, String currencyCode, int id) {

        return new AutoValue_RoomCoin(name, symbol, change24h, price, rank, currencyCode, id);
    }

    @Override
    @PrimaryKey
    @AutoValue.CopyAnnotations
    public abstract int id();

}
