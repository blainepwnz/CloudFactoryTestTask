package com.tomash.poloniexupdater.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Ticker implements Parcelable {

    private String name;
    private int id;
    private String last;
    private String highestBid;
    private String percentChange;

    protected Ticker(Parcel in) {
        name = in.readString();
        id = in.readInt();
        last = in.readString();
        highestBid = in.readString();
        percentChange = in.readString();
    }

    public static final Creator<Ticker> CREATOR = new Creator<Ticker>() {
        @Override
        public Ticker createFromParcel(Parcel in) {
            return new Ticker(in);
        }

        @Override
        public Ticker[] newArray(int size) {
            return new Ticker[size];
        }
    };

    public String getName() {
        return name;
    }

    public Ticker setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Used to copy into this object all data from other ticker
     */
    public void copyFromOther(Ticker ticker) {
        name = ticker.name;
        id = ticker.id;
        last = ticker.last;
        highestBid = ticker.highestBid;
        percentChange = ticker.percentChange;
    }

    public int getId() {
        return id;
    }

    public String getLast() {
        return last;
    }

    public String getHighestBid() {
        return highestBid;
    }

    public String getPercentChange() {
        return percentChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticker ticker = (Ticker) o;

        if (id != ticker.id) return false;
        if (!name.equals(ticker.name)) return false;
        if (last != null ? !last.equals(ticker.last) : ticker.last != null) return false;
        if (highestBid != null ? !highestBid.equals(ticker.highestBid) : ticker.highestBid != null)
            return false;
        return percentChange != null ? percentChange.equals(ticker.percentChange) : ticker.percentChange == null;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Ticker{" +
            "name='" + name + '\'' +
            ", id=" + id +
            ", last='" + last + '\'' +
            ", highestBid='" + highestBid + '\'' +
            ", percentChange='" + percentChange + '\'' +
            '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeString(last);
        parcel.writeString(highestBid);
        parcel.writeString(percentChange);
    }
}
