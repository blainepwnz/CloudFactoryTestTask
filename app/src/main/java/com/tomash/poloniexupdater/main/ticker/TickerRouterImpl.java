package com.tomash.poloniexupdater.main.ticker;

import com.tomash.poloniexupdater.base.mvp.router.AbstractRouter;
import com.tomash.poloniexupdater.base.mvp.view.BaseActivity;
import com.tomash.poloniexupdater.main.ticker.di.TickerScope;

import javax.inject.Inject;

@TickerScope
public class TickerRouterImpl extends AbstractRouter implements TickerRouter {

    @Inject
    public TickerRouterImpl(BaseActivity activityContext) {
        super(activityContext);
    }

}
