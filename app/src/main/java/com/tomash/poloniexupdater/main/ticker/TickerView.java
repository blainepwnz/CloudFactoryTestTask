package com.tomash.poloniexupdater.main.ticker;

import com.tomash.poloniexupdater.base.mvp.view.BaseView;

public interface TickerView extends BaseView {
    void showToast(String errorMessage);
}
