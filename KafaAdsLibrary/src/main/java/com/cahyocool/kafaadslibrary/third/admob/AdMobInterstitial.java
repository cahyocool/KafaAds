package com.cahyocool.kafaadslibrary.third.admob;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.kafaads.kafaadslibrary.BuildConfig;
import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;

public class AdMobInterstitial extends BaseInterstitialThirdParty {
    private InterstitialAd mInterAd;
    private AdRequest adRequest;
    private static final String TAG = AdMobInterstitial.class.getSimpleName();
    private long startTime, endTime;
    private Ad ad;
    private Context context;

    public AdMobInterstitial(@NonNull Context context, @NonNull Ad ad) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "/AdMobInterstitial");
        }
        this.context = context;
        this.ad = ad;
        adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(KafaAds.activity, ad.key, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        //super.onAdLoaded(interstitialAd);
                        endTime = System.currentTimeMillis();
                        mInterAd = interstitialAd;
                        if (KafaAds.TEST) {
                            Log.d(KafaAds.TAG, "#1 onAdLoaded() - type: [ ADMOB ], kind: [ INTERSTITIAL ], CURRENT_TIME_MILLIS: " + String.valueOf((endTime - startTime) / 1000) + "초");
                        }

                        if (onAdLoadListener != null) {
                            onAdLoadListener.onAdLoaded(ad, null);
                        }
                        mInterAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Log.d("TAG", "The ad was dismissed.");
                                        mInterAd = null;
                                        endTime = System.currentTimeMillis();
                                        if (KafaAds.TEST) {
                                            Log.d(KafaAds.TAG, "#1 onAdClosed() - type: [ ADMOB ], kind: [ INTERSTITIAL ], CURRENT_TIME_MILLIS: " + String.valueOf((endTime - startTime) / 1000) + "초");
                                        }

                                        if (onAdLoadListener != null) {
                                            onAdLoadListener.onAdClosed(ad);
                                        }
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        Log.d("TAG", "The ad failed to show.");
                                        mInterAd = null;
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        //super.onAdFailedToLoad(loadAdError);
                        endTime = System.currentTimeMillis();
                        mInterAd = null;
                        if (KafaAds.TEST) {
                            Log.d(KafaAds.TAG, "#1 onAdFailedToLoad() - type: [ ADMOB ], kind: [ INTERSTITIAL ], CURRENT_TIME_MILLIS: " + String.valueOf((endTime - startTime) / 1000) + "초");
                            Log.d(KafaAds.TAG, "#1 onAdFailedToLoad() - type: [ ADMOB ], kind: [ INTERSTITIAL ], errorCode: " + loadAdError);
                        }
                        if (onAdLoadListener != null) {
                            onAdLoadListener.onAdFailedToLoad(ad);
                        }
                    }

                });
    }

    @Override
    public void loadPreparedAd() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void showPreparedAd() {
        mInterAd.show(KafaAds.activity);
    }
}
