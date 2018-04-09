package com.tomash.poloniexupdater.main.di;

import android.support.v4.app.FragmentManager;

import com.tomash.poloniexupdater.api.PoloniexApi;
import com.tomash.poloniexupdater.base.di.AppComponent;
import com.tomash.poloniexupdater.base.mvp.view.BaseActivity;
import com.tomash.poloniexupdater.infra.ViewPagerStatusProvider;
import com.tomash.poloniexupdater.main.MainActivity;
import com.tomash.poloniexupdater.main.MainView;

import dagger.BindsInstance;
import dagger.Component;

@MainScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainActivity activity);

    PoloniexApi api();

    ViewPagerStatusProvider viewPagerStatusProvider();

    @Component.Builder
    interface Builder {
        MainComponent build();

        @BindsInstance
        Builder fragmentManager(FragmentManager manager);

        Builder appComponent(AppComponent component);

        @BindsInstance
        Builder mainView(MainView view);

        @BindsInstance
        Builder baseActivity(BaseActivity activity);
    }
}
