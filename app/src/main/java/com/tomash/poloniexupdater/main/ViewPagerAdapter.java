package com.tomash.poloniexupdater.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tomash.poloniexupdater.main.blank.BlankFragment;
import com.tomash.poloniexupdater.main.di.MainScope;
import com.tomash.poloniexupdater.main.ticker.TickerFragment;

import javax.inject.Inject;

@MainScope
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int FRAGMENTS_COUNT = 2;
    public static final int TICKER_POSITION = 0;

    @Inject
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == TICKER_POSITION)
            return TickerFragment.createInstance(position);
        return BlankFragment.createInstance();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == TICKER_POSITION) {
            return "Ticker";
        }
        return "Kitty";
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
