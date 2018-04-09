package com.tomash.poloniexupdater.infra.interactors;

import com.tomash.poloniexupdater.entity.Ticker;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface GetTickerInteractor {

    Single<List<Ticker>> getAllTickers();

    Observable<List<Ticker>> getAllTickersEvery(long intervalMillis);

}
