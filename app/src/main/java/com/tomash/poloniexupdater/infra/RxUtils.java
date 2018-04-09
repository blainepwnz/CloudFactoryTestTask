package com.tomash.poloniexupdater.infra;

import io.reactivex.disposables.Disposable;

public class RxUtils {

    /**
     * Disposes current subscription
     * @param subscription can be null
     */
    public static void disposeDisposable(Disposable subscription) {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
