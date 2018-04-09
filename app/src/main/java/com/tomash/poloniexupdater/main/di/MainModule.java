package com.tomash.poloniexupdater.main.di;

import com.tomash.poloniexupdater.main.MainPresenter;
import com.tomash.poloniexupdater.main.MainPresenterImpl;
import com.tomash.poloniexupdater.main.MainRouter;
import com.tomash.poloniexupdater.main.MainRouterImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainModule {

    @Binds
    @MainScope
    abstract MainPresenter presenter(MainPresenterImpl mainPresenter);

    @Binds
    @MainScope
    abstract MainRouter router(MainRouterImpl mainRouter);

}
