package com.cahyocool.kafaadslibrary.third.applovin;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.kafaads.kafaadslibrary.BuildConfig;

import java.util.concurrent.TimeUnit;

public class MaxInterstitial extends BaseAppLovinInterstitial {
    private MaxInterstitialAd mInterAd;
    private int retryAttempt;
    private static final String TAG = MaxInterstitial.class.getSimpleName();
    private Ad ad;
    private Context context;

    public MaxInterstitial(@NonNull Context context, @NonNull Ad adx) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "/AppLovinInterstitial");
        }
        this.context = context;
        this.ad = adx;
        mInterAd = new MaxInterstitialAd(adx.key, KafaAds.activity);
        mInterAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {
                retryAttempt = 0;
                if (onAppLovinAdListener != null) {
                    onAppLovinAdListener.onAdLoaded(adx, null);
                }
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {
                if (onAppLovinAdListener != null) {
                    onAppLovinAdListener.onAdHidden(adx);
                }
            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                retryAttempt++;
                long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mInterAd.loadAd();
                    }
                }, delayMillis);
                if (onAppLovinAdListener != null) {
                    onAppLovinAdListener.onAdFailedToLoad(adx, error);
                }
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                mInterAd.loadAd();
            }
        });
    }

    @Override
    public void loadPreparedAd() {
        mInterAd.loadAd();
    }

    @Override
    public void showPreparedAd() {
        if (mInterAd.isReady()) {
            mInterAd.showAd();
        }
    }
}
