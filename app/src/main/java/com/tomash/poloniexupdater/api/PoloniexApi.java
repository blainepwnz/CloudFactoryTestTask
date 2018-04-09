package com.tomash.poloniexupdater.api;

import com.tomash.poloniexupdater.entity.Ticker;

import java.util.Map;

import retrofit2.http.GET;
import io.reactivex.Single;

public interface PoloniexApi {

    @GET("public?command=returnTicker")
    Single<Map<String, Ticker>> getTickers();
}
