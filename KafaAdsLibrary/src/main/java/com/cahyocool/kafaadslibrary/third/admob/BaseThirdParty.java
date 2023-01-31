package com.cahyocool.kafaadslibrary.third.admob;

public abstract class BaseThirdParty {
    public OnAdLoadListener onAdLoadListener;

    public void setOnAdLoadListener(OnAdLoadListener onAdLoadListener) {
        this.onAdLoadListener = onAdLoadListener;
    }

    public abstract void loadPreparedAd();
}
