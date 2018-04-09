package com.tomash.poloniexupdater.base.mvp.router;

import android.content.Intent;
import android.net.Uri;

import com.tomash.poloniexupdater.base.mvp.view.BaseActivity;

public abstract class AbstractRouter implements BaseRouter {

    private static final String TAG = "AbstractRouter";
    protected BaseActivity activityContext;

    public AbstractRouter(BaseActivity activityContext) {
        this.activityContext = activityContext;
    }

    @Override
    public void goBack() {
        activityContext.superOnBackPressed();
    }

    protected void launchActivityWithActionView(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activityContext.startActivity(i);
    }

    @Override
    public void finish() {
        activityContext.finish();
    }
}
