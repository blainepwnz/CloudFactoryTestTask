package com.tomash.poloniexupdater.base.mvp.presenter;

import android.os.Bundle;

public interface BasePresenter {

    /**
     * Called after create of presenter
     */
    void onCreate(Bundle savedInstanceState, Bundle createIntent);

    /**
     * Delegate for saveInstanceState from fragment or activity
     */
    void onSaveInstanceState(Bundle out);

    /**
     * Called on destroy of presenter
     */
    void onDestroy();

    /**
     * Called when view is gone to background
     */
    void onPause();

    /**
     * Called when on view is ready to be shown to user
     */
    void onResume();

}
