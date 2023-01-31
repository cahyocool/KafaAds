package com.cahyocool.kafaadslibrary.third.applovin;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.sdk.AppLovinSdk;

public class MaxAppOpen implements LifecycleObserver, MaxAdListener {
    private MaxAppOpenAd appOpenAd;
    private Context context;
    private String OpenAdId;

    public MaxAppOpen(Context context, String OpenAdId) {
        this.context = context;
        this.OpenAdId = OpenAdId;
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        appOpenAd = new MaxAppOpenAd(OpenAdId, context);
        appOpenAd.setListener(this);
        appOpenAd.loadAd();
    }

    private void showAdIfReady() {
        if (appOpenAd == null || !AppLovinSdk.getInstance(context).isInitialized()) return;
        if (appOpenAd.isReady()) {
            appOpenAd.showAd();
        } else {
            appOpenAd.loadAd();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        showAdIfReady();
    }

    @Override
    public void onAdLoaded(MaxAd ad) {

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {
        appOpenAd.loadAd();
    }

    @Override
    public void onAdClicked(MaxAd ad) {

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
        appOpenAd.loadAd();
    }
}
