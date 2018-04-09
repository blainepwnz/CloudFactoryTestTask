package com.tomash.poloniexupdater.infra;

import android.support.v4.view.ViewPager;

import com.tomash.poloniexupdater.main.di.MainScope;

import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
@MainScope
public class ViewPagerStatusProvider extends ViewPager.SimpleOnPageChangeListener {
    private static final String TAG = "ViewPagerStatusProvider";
    private int position;
    private int currentViewPagerState;
    private Subject<Integer> viewPagerStateSubject = PublishSubject.create();
    private Subject<Integer> viewPagerPositionSubject = PublishSubject.create();

    @Inject
    public ViewPagerStatusProvider() {
    }

    public boolean isIdle() {
        return currentViewPagerState == ViewPager.SCROLL_STATE_IDLE;
    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        viewPagerPositionSubject.onNext(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        currentViewPagerState = state;
        viewPagerStateSubject.onNext(state);
    }

    /**
     * Defines currently selected viewpager position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Gets publisher for all state changes of viewpager(idle, dragging , settling)
     */
    public Subject<Integer> getViewPagerStateSubject() {
        return viewPagerStateSubject;
    }

    /**
     * Gets publisher for position changes in viewpager
     */
    public Subject<Integer> getViewPagerPositionSubject() {
        return viewPagerPositionSubject;
    }
}
