package com.tomash.poloniexupdater.main.ticker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tomash.poloniexupdater.R;
import com.tomash.poloniexupdater.base.mvp.view.BaseFragment;
import com.tomash.poloniexupdater.main.MainActivity;
import com.tomash.poloniexupdater.main.ticker.di.DaggerTickerComponent;

import javax.inject.Inject;

import butterknife.BindView;

public class TickerFragment extends BaseFragment<TickerPresenter> implements TickerView {

    public static final String POSITION_ARG = "position_arg";

    @Inject
    TickerRvAdapter tickerRvAdapter;

    @BindView(R.id.tickers_rv)
    RecyclerView tickerRv;

    public static TickerFragment createInstance(int position) {
        Bundle createBundle = new Bundle();
        createBundle.putInt(POSITION_ARG, position);
        TickerFragment fragment = new TickerFragment();
        fragment.setArguments(createBundle);
        return fragment;
    }

    @Override
    public void initViews() {
        super.initViews();
        tickerRv.setLayoutManager(new GridLayoutManager(getBaseActivity(), 3));
        tickerRv.setAdapter(tickerRvAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ticker;
    }

    @Override
    protected void inject() {
        DaggerTickerComponent.builder()
            .baseActivity(getBaseActivity())
            .tickerView(this)
            .mainComponent(((MainActivity) getBaseActivity()).getMainComponent())
            .build()
            .inject(this);
    }

    @Override
    public void showToast(String errorMessage) {
        Toast.makeText(getBaseActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }
}
