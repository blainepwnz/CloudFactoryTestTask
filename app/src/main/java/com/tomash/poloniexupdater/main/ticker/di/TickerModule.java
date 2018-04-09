package com.tomash.poloniexupdater.main.ticker.di;

import com.tomash.poloniexupdater.infra.interactors.GetTickerInteractor;
import com.tomash.poloniexupdater.infra.interactors.GetTickerInteractorImpl;
import com.tomash.poloniexupdater.main.ticker.TickerPresenter;
import com.tomash.poloniexupdater.main.ticker.TickerPresenterImpl;
import com.tomash.poloniexupdater.main.ticker.TickerRouter;
import com.tomash.poloniexupdater.main.ticker.TickerRouterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TickerModule {

    @Binds
    @TickerScope
    abstract TickerPresenter presenter(TickerPresenterImpl presenter);

    @Binds
    @TickerScope
    abstract TickerRouter router(TickerRouterImpl router);

    @Binds
    @TickerScope
    abstract GetTickerInteractor getTickerInteractor(GetTickerInteractorImpl getTickerInteractor);

}
