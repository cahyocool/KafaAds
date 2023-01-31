package com.cahyocool.kafaadslibrary.third.applovin;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.kafaads.kafaadslibrary.BuildConfig;

import java.util.concurrent.TimeUnit;

public class MaxRewards extends BaseAppLovinReward {
    private MaxRewardedAd rewardedAd;
    private int retryAttempt;
    private static final String TAG = MaxRewards.class.getSimpleName();
    private Ad adx;
    private Context context;

    public MaxRewards(@NonNull Context context, @NonNull Ad adx) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "/AppLovinAdMobReward");
        }

        this.adx = adx;
        this.context = context;

        rewardedAd = MaxRewardedAd.getInstance(adx.key, KafaAds.activity);
        rewardedAd.setListener(new MaxRewardedAdListener() {
            @Override
            public void onRewardedVideoStarted(MaxAd ad) {

            }

            @Override
            public void onRewardedVideoCompleted(MaxAd ad) {

            }

            @Override
            public void onUserRewarded(MaxAd ad, com.applovin.mediation.MaxReward reward) {

            }

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
                        rewardedAd.loadAd();
                    }
                }, delayMillis);
                if (onAppLovinAdListener != null) {
                    onAppLovinAdListener.onAdFailedToLoad(adx, error);
                }
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });
    }

    @Override
    public void loadPreparedAd() {
        rewardedAd.loadAd();
    }

    @Override
    public void showPreparedAd() {
        if (rewardedAd.isReady()) {
            rewardedAd.showAd();
        }
    }
}
