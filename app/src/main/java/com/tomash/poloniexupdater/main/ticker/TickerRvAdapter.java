package com.tomash.poloniexupdater.main.ticker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomash.poloniexupdater.R;
import com.tomash.poloniexupdater.entity.Ticker;
import com.tomash.poloniexupdater.main.ticker.di.TickerScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@TickerScope
public class TickerRvAdapter extends RecyclerView.Adapter<TickerRvAdapter.ViewHolder> {

    private SparseArray<Ticker> tickerSparseArray;
    private List<Ticker> tickerList;
    private static final String TAG = "TickerRvAdapter";
    private final Object lock = new Object();

    @Inject
    public TickerRvAdapter() {
        tickerSparseArray = new SparseArray<>();
        tickerList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tickerList.get(position));
    }

    /**
     * Used to init this adapter
     */
    private void initTickers(List<Ticker> tickers) {
        Log.d(TAG, "updateTickers: initiating ticker adapter");
        for (Ticker ticker : tickers) {
            //saving every ticker in sparse array
            tickerSparseArray.put(ticker.getId(), ticker);
        }
        tickerList.addAll(tickers);
        notifyDataSetChanged();
    }

    /**
     * Used to update tickers with new data
     */
    public void updateTickers(List<Ticker> tickers) {
        synchronized (lock) {
            if (tickerSparseArray.size() == 0) {
                initTickers(tickers);
                return;
            }
            for (Ticker updated : tickers) {
                Ticker old = tickerSparseArray.get(updated.getId());
                //if there is no such ticker in sparse array - add it and update rv
                if (old == null) {
                    Log.d(TAG, "updateTickers: added new ticker to list " + updated);
                    tickerSparseArray.put(updated.getId(), updated);
                    tickerList.add(updated);
                    notifyItemInserted(tickerList.size() - 1);
                    continue;
                }
                //if tickers are not equal - copy data from new to old and update adapter
                if (!old.equals(updated)) {
                    Log.d(TAG, "updateTickers: updated ticker " + updated);
                    old.copyFromOther(updated);
                    notifyItemChanged(tickerList.indexOf(old));
                }
            }
        }
    }

    public List<Ticker> getCurrentItems() {
        synchronized (lock) {
            return tickerList;
        }
    }

    @Override
    public int getItemCount() {
        return tickerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ticker_name)
        TextView nameTv;
        @BindView(R.id.tv_ticker_last)
        TextView lastTv;
        @BindView(R.id.tv_ticker_highest_bid)
        TextView highestBidTv;
        @BindView(R.id.tv_ticker_percent_change)
        TextView percentChangeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Ticker ticker) {
            Context ctx = itemView.getContext();
            nameTv.setText(ticker.getName());
            lastTv.setText(ctx.getString(R.string.last_template, ticker.getLast()));
            highestBidTv.setText(ctx.getString(R.string.highest_bid_template, ticker.getHighestBid()));
            percentChangeTv.setText(ctx.getString(R.string.percent_change_template, ticker.getPercentChange()));
        }
    }


}
