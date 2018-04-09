package com.tomash.poloniexupdater.main.ticker.di;

import com.tomash.poloniexupdater.base.mvp.view.BaseActivity;
import com.tomash.poloniexupdater.main.di.MainComponent;
import com.tomash.poloniexupdater.main.ticker.TickerFragment;
import com.tomash.poloniexupdater.main.ticker.TickerView;

import dagger.BindsInstance;
import dagger.Component;

@TickerScope
@Component(modules = TickerModule.class, dependencies = MainComponent.class)
public interface TickerComponent {
    void inject(TickerFragment fragment);

    @Component.Builder
    interface Builder {
        TickerComponent build();

        @BindsInstance
        Builder baseActivity(BaseActivity baseActivity);

        @BindsInstance
        Builder tickerView(TickerView view);

        Builder mainComponent(MainComponent component);
    }
}
