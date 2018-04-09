package com.tomash.poloniexupdater.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tomash.poloniexupdater.R;
import com.tomash.poloniexupdater.base.mvp.view.BaseActivity;
import com.tomash.poloniexupdater.infra.ViewPagerStatusProvider;
import com.tomash.poloniexupdater.main.di.DaggerMainComponent;
import com.tomash.poloniexupdater.main.di.MainComponent;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    private static final String TAG = "MainActivity";

    private MainComponent mainComponent;

    @Inject
    ViewPagerStatusProvider viewPagerStatusProvider;

    @Inject
    ViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void inject() {
        mainComponent = DaggerMainComponent.builder()
            .appComponent(getPoloniexApp().getComponent())
            .fragmentManager(getSupportFragmentManager())
            .mainView(this)
            .baseActivity(this)
            .build();
        mainComponent.inject(this);
    }

    @Override
    public void initViews() {
        super.initViews();
        initViewPager();
    }

    private void initViewPager() {
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerStatusProvider);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }
}
