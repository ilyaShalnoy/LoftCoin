package com.example.loftcoin.data;

public interface Coin {

    int id();

    String name();

    String symbol();

    double change24h();

    int rank();

    double price();

}
