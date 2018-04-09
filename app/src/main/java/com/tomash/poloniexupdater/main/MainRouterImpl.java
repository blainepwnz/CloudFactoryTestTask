package com.tomash.poloniexupdater.main;

import com.tomash.poloniexupdater.base.mvp.router.AbstractRouter;
import com.tomash.poloniexupdater.base.mvp.view.BaseActivity;
import com.tomash.poloniexupdater.main.di.MainScope;

import javax.inject.Inject;

@MainScope
public class MainRouterImpl extends AbstractRouter implements MainRouter {

    @Inject
    public MainRouterImpl(BaseActivity activityContext) {
        super(activityContext);
    }
}
