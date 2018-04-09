package com.tomash.poloniexupdater.infra.interactors;

import com.tomash.poloniexupdater.api.PoloniexApi;
import com.tomash.poloniexupdater.entity.Ticker;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;

public class GetTickerInteractorImpl implements GetTickerInteractor {

    private PoloniexApi poloniexApi;

    @Inject
    public GetTickerInteractorImpl(PoloniexApi poloniexApi) {
        this.poloniexApi = poloniexApi;
    }

    @Override
    public Single<List<Ticker>> getAllTickers() {
        return poloniexApi.getTickers()
            .compose(tickerNameTransformer());
    }

    @Override
    public Observable<List<Ticker>> getAllTickersEvery(long intervalMillis) {
        return Observable.interval(0, intervalMillis, TimeUnit.MILLISECONDS)
            .flatMapSingle(aLong -> getAllTickers());
    }

    private SingleTransformer<Map<String, Ticker>, List<Ticker>> tickerNameTransformer() {
        return mapSingle -> mapSingle.flatMap(stringTickerMap ->
            Observable.fromIterable(stringTickerMap.keySet())
                .map(name -> stringTickerMap.get(name).setName(name))
                .toList());
    }
}
