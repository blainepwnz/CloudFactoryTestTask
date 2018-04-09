package com.tomash.poloniexupdater.main.ticker;

import android.os.Bundle;
import android.util.Log;

import com.tomash.poloniexupdater.base.mvp.presenter.AbstractPresenter;
import com.tomash.poloniexupdater.entity.Ticker;
import com.tomash.poloniexupdater.infra.RxUtils;
import com.tomash.poloniexupdater.infra.ViewPagerStatusProvider;
import com.tomash.poloniexupdater.infra.interactors.GetTickerInteractor;
import com.tomash.poloniexupdater.main.ticker.di.TickerScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@TickerScope
public class TickerPresenterImpl extends AbstractPresenter<TickerView, TickerRouter> implements TickerPresenter {

    private static final String TAG = "TickerPresenter";
    private static final String TICKER_PARCEL = "ticker parcel";
    private static final long UPDATE_INTERVAL = 5000;
    private GetTickerInteractor getTickerInteractor;
    private TickerRvAdapter tickerRvAdapter;
    private ViewPagerStatusProvider viewPagerStatusProvider;
    private int fragmentPosition;
    private Disposable tickerDisposable;

    @Inject
    public TickerPresenterImpl(TickerView view, TickerRouter router, GetTickerInteractor getTickerInteractor,
                               TickerRvAdapter tickerRvAdapter, ViewPagerStatusProvider viewPagerStatusProvider) {
        super(view, router);
        this.getTickerInteractor = getTickerInteractor;
        this.tickerRvAdapter = tickerRvAdapter;
        this.viewPagerStatusProvider = viewPagerStatusProvider;
    }

    @Override
    protected boolean canBeCreated() {
        return true;
    }

    @Override
    protected void onSuccessfulCreate(Bundle savedInstanceState, Bundle args) {
        super.onSuccessfulCreate(savedInstanceState, args);
        initPositionFromArgs(args);
        subscribeToViewpagerUpdates();
        subscribeToTickerUpdates();
        checkForSavedInstanceState(savedInstanceState);
    }

    private void checkForSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(TICKER_PARCEL)) {
            List<Ticker> savedTickers = savedInstanceState.getParcelableArrayList(TICKER_PARCEL);
            tickerRvAdapter.updateTickers(savedTickers);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelableArrayList(TICKER_PARCEL, new ArrayList<>(tickerRvAdapter.getCurrentItems()));
    }

    private void initPositionFromArgs(Bundle args) {
        fragmentPosition = args.getInt(TickerFragment.POSITION_ARG, -1);
        if (fragmentPosition == -1) {
            throw new IllegalStateException("Should contain position in arguments to TickerFragment");
        }
    }

    private void subscribeToTickerUpdates() {
        Log.d(TAG, "subscribeToTickerUpdates: subscribed to ticker updates");
        RxUtils.disposeDisposable(tickerDisposable);
        tickerDisposable = getTickerInteractor.getAllTickersEvery(UPDATE_INTERVAL)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(tickers -> Log.d(TAG, "subscribeToTickerUpdates: got updated tickers"))
            .subscribe(tickers -> tickerRvAdapter.updateTickers(tickers),
                throwable -> view.showToast(getErrorMessage(throwable)));
        addSubscription(tickerDisposable);
    }

    /**
     * Subscribes to updates of position of viewpager, in case if position is not position of ticker fragment
     * - unsubscribe from updates, otherwise subscribe to updates
     */
    private void subscribeToViewpagerUpdates() {
        addSubscription(viewPagerStatusProvider.getViewPagerPositionSubject()
            .doOnNext(position -> Log.d(TAG, "subscribeToViewpagerUpdates: changed position of viewpager " + position))
            .subscribe(position -> {
                if (position == fragmentPosition)
                    subscribeToTickerUpdates();
                else
                    RxUtils.disposeDisposable(tickerDisposable);
            }));
    }
}
