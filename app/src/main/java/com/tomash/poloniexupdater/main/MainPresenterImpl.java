package com.tomash.poloniexupdater.main;

import android.os.Bundle;

import com.tomash.poloniexupdater.base.mvp.presenter.AbstractPresenter;
import com.tomash.poloniexupdater.main.di.MainScope;

import javax.inject.Inject;

@MainScope
public class MainPresenterImpl extends AbstractPresenter<MainView, MainRouter> implements MainPresenter {

    @Inject
    public MainPresenterImpl(MainView view, MainRouter router) {
        super(view, router);
    }

    @Override
    protected boolean canBeCreated() {
        //if there would be some authorization or objects this presenter should depend on - there would be some logic here
        return true;
    }

}
