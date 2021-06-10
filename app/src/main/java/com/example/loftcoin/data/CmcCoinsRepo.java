package com.example.loftcoin.data;

import androidx.annotation.NonNull;

import com.example.loftcoin.BuildConfig;
import com.squareup.moshi.Moshi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Singleton
class CmcCoinsRepo implements CoinsRepo {

    private final CmcApi api;

    @Inject
    public CmcCoinsRepo(CmcApi api) {
        this.api = api;
    }

    @NotNull
    @Override
    public List<? extends Coin> listings(@NonNull @NotNull String currency) throws IOException {
        final Response<Listings> response = api.listings(currency).execute();
        if (response.isSuccessful()) {
            final Listings listings = response.body();
            if(listings != null) {
                return listings.data();
            }
        } else {
            final ResponseBody responseBody = response.errorBody();
            if (responseBody != null) {
                throw new IOException(responseBody.string());
            }
        }
        return Collections.emptyList();
    }
}
