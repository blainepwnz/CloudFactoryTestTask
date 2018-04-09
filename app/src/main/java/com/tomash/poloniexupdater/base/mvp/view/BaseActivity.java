package com.tomash.poloniexupdater.base.mvp.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.tomash.poloniexupdater.base.PoloniexApp;
import com.tomash.poloniexupdater.base.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<Presenter extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected final String TAG = getTag();

    @Inject
    protected Presenter presenter;

    private Unbinder unbinder;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        //init presenter after super
        presenter.onCreate(savedInstanceState, getStartingIntentBundle());
    }

    /**
     * Injects all dependencies using dagger
     */
    protected abstract void inject();

    private Bundle getStartingIntentBundle() {
        if (getIntent() != null)
            return getIntent().getExtras();
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        presenter.onResume();
    }

    @Override
    public void initViews() {
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected final void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        presenter.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    protected abstract String getTag();

    protected abstract int getContentViewId();

    /**
     *
     */
    public void replaceFragment(int containerId, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
            .beginTransaction()
            .replace(containerId, fragment, tag);
        if (addToBackStack)
            transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    /**
     * Helper method for dismissing dialogs
     */
    protected void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    public PoloniexApp getPoloniexApp() {
        return (PoloniexApp) getApplication();
    }

    /**
     * Default back press
     */
    public void superOnBackPressed() {
        super.onBackPressed();
    }
}
