package com.cahyocool.kafaadslibrary.third.admob;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import androidx.annotation.NonNull;

import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.cahyocool.kafaadslibrary.data.AdType;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class AdMobBanner extends BaseThirdParty {
    private AdView adView;
    private static final String TAG = AdMobBanner.class.getSimpleName();
    private long startTime, endTime;
    private Context ctx;

    public AdMobBanner(@NonNull Context context, @NonNull final Ad ad) {
        ctx = context;
        adView = new AdView(context);
        adView.setAdSize(ad.type == AdType.BANNER ? getAdSize() : ad.type == AdType.HALF_BANNER ? AdSize.MEDIUM_RECTANGLE : null);
        adView.setAdUnitId(ad.key);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                endTime = System.currentTimeMillis();
                if (onAdLoadListener != null) {
                    if (KafaAds.TEST) {
                        if (ad.type == AdType.HALF_BANNER) {
                            Log.d(KafaAds.TAG, "#1 onAdLoaded() - type: [ ADMOB ], kind: [ HALF_BANNER ], CURRENT_TIME_MILLIS: " + String.valueOf((endTime - startTime) / 1000) + "초");
                        } else {
                            Log.d(KafaAds.TAG, "#1 onAdLoaded() - type: [ ADMOB ], kind: [ BANNER ], CURRENT_TIME_MILLIS: " + String.valueOf((endTime - startTime) / 1000) + "초");
                        }
                    }
                    onAdLoadListener.onAdLoaded(ad, adView);
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError errorCode) {
                super.onAdFailedToLoad(errorCode);
                endTime = System.currentTimeMillis();
                if (KafaAds.TEST) {
                    if (ad.type == AdType.HALF_BANNER) {
                        Log.d(KafaAds.TAG, "#1 onAdFailed() - type: [ ADMOB ], kind: [ HALF_BANNER ], CURRENT_TIME_MILLIS: " + String.valueOf((endTime - startTime) / 1000) + "초");
                        Log.d(KafaAds.TAG, "#1 onAdFailed() - type: [ ADMOB ], kind: [ HALF_BANNER ], errorCode: " + errorCode);
                    } else {
                        Log.d(KafaAds.TAG, "#1 onAdFailed() - type: [ ADMOB ], kind: [ BANNER ], CURRENT_TIME_MILLIS: " + String.valueOf((endTime - startTime) / 1000) + "초");
                        Log.d(KafaAds.TAG, "#1 onAdFailed() - type: [ ADMOB ], kind: [ BANNER ], errorCode: " + errorCode);
                    }
                }
                if (onAdLoadListener != null) {
                    onAdLoadListener.onAdFailedToLoad(ad);
                }
            }
        });
    }

    public AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = KafaAds.activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(ctx, adWidth);
    }

    @Override
    public void loadPreparedAd() {
        startTime = System.currentTimeMillis();
        adView.loadAd(new AdRequest.Builder().build());
    }
}
