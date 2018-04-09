package com.tomash.poloniexupdater.base;

import android.app.Application;

import com.tomash.poloniexupdater.base.di.AppComponent;
import com.tomash.poloniexupdater.base.di.AppModule;
import com.tomash.poloniexupdater.base.di.DaggerAppComponent;
import com.tomash.poloniexupdater.base.di.NetworkModule;

public class PoloniexApp extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
    }

    private void initComponent() {
        component = DaggerAppComponent.builder()
            .appModule(new AppModule())
            .networkModule(new NetworkModule())
            .context(this)
            .app(this)
            .build();
    }

    public AppComponent getComponent() {
        return component;
    }
}
