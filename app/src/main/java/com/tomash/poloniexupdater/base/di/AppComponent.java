package com.tomash.poloniexupdater.base.di;

import android.content.Context;

import com.tomash.poloniexupdater.api.PoloniexApi;
import com.tomash.poloniexupdater.base.PoloniexApp;

import dagger.BindsInstance;
import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    Context context();

    PoloniexApi api();

    @Component.Builder
    interface Builder {
        AppComponent build();

        Builder networkModule(NetworkModule module);

        Builder appModule(AppModule module);

        @BindsInstance
        Builder context(Context context);

        @BindsInstance
        Builder app(PoloniexApp app);
    }
}
