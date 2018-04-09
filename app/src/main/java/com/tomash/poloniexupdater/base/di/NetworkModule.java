package com.tomash.poloniexupdater.base.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tomash.poloniexupdater.BuildConfig;
import com.tomash.poloniexupdater.api.PoloniexApi;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This module provides everything that is related to network, retrofit or okHttp
 */

@Module
public class NetworkModule {

    @Provides
    @AppScope
    Gson providesGson() {
        return new GsonBuilder()
            .create();
    }

    @Provides
    @AppScope
    protected OkHttpClient.Builder providesOkHttpBuilder(HttpLoggingInterceptor httpLoggingInterceptor) {

        return new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor);
    }


    @Provides
    @AppScope
    protected HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @Provides
    @AppScope
    Retrofit.Builder providesRetrofitBuilder(Gson gson) {
        return new Retrofit.Builder()
            .baseUrl(BuildConfig.HOST)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(gson));
    }

    @Provides
    @AppScope
    OkHttpClient providesOkHttpClient(OkHttpClient.Builder authorizedOkHttpBuilder) {
        return authorizedOkHttpBuilder
            //headers interceptors can be added here later
            .build();
    }

    @Provides
    @AppScope
    Retrofit providesRetrofit(Retrofit.Builder retrofitBuilder, OkHttpClient okHttpClient) {
        return retrofitBuilder
            .client(okHttpClient)
            .build();
    }

    @Provides
    @AppScope
    PoloniexApi providePoloniexApi(Retrofit retrofit) {
        return retrofit.create(PoloniexApi.class);
    }

}
