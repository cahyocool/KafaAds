package com.cahyocool.kafaadslibrary.third.applovin;

public abstract class BaseAppLovin {
    public OnAppLovinAdListener onAppLovinAdListener;

    public void setOnAppLovinAdListener(OnAppLovinAdListener onAppLovinAdListener) {
        this.onAppLovinAdListener = onAppLovinAdListener;
    }

    public abstract void loadPreparedAd();
}
