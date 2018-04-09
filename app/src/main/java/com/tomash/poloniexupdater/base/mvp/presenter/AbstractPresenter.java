package com.tomash.poloniexupdater.base.mvp.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.tomash.poloniexupdater.base.mvp.router.BaseRouter;
import com.tomash.poloniexupdater.base.mvp.view.BaseView;
import com.tomash.poloniexupdater.infra.RxUtils;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public abstract class AbstractPresenter<View extends BaseView, Router extends BaseRouter> implements BasePresenter {

    private static final String TAG = "AbstractPresenter";

    protected View view;
    protected Router router;
    private CompositeDisposable compositeSubscription;
    /**
     * Defines if this presenter was initiated properly
     */
    private boolean isInitiated;

    public AbstractPresenter(View view, Router router) {
        this.view = view;
        this.router = router;
        this.compositeSubscription = new CompositeDisposable();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState, Bundle createIntent) {
        if (canBeCreated()) {
            onSuccessfulCreate(savedInstanceState, createIntent);
        } else {
            onUnsuccessfulCreate();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle out) {

    }

    /**
     * Called when view is ready to be initiated
     */
    protected void onSuccessfulCreate(Bundle savedInstanceState, Bundle args) {
        isInitiated = true;
        view.initViews();
    }

    protected abstract boolean canBeCreated();

    /**
     * Called when view is not initiated and it should be finished
     */
    protected void onUnsuccessfulCreate() {
        router.finish();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    /**
     * Default method for parsing throwables and doing logic in case of error
     */
    protected String getErrorMessage(Throwable throwable) {
        String message = throwable.getMessage();
        if (!TextUtils.isEmpty(message)) {
            return message;
        }
        return "Unknown Error";
    }

    /**
     * Adds disposable to composite subscription to  dispose it on destroy
     *
     * @param subscription that needs to be utilized later
     */
    protected void addSubscription(Disposable subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public final void onDestroy() {
        view = null;
        RxUtils.disposeDisposable(compositeSubscription);
        if (isInitiated) {
            onSuccessfulDestroy();
        }
    }

    protected <T> void subscribeToEvents(Observable<T> subject, Consumer<T> onNext) {
        addSubscription(subject.subscribe(onNext));
    }

    /**
     * Called if view was properly initialized and presenter should be destroyed soon
     */
    protected void onSuccessfulDestroy() {

    }
}
