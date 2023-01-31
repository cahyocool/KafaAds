package com.cahyocool.kafaadslibrary.third.admob;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.third.applovin.MaxAppOpen;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "AppOpenManager";
    public static String AD_UNIT_ID = "";
    private AppOpenAd appOpenAd = null;
    private Activity currentActivity;
    private static boolean isShowingAd = false;
    private long loadTime = 0;

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private final Application myApplication;

    /**
     * Constructor
     */
    public AppOpenManager(Application myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
        showAdIfAvailable();
        Log.d(LOG_TAG, "onStart");
    }

    /**
     * Shows the ad if one isn't already showing.
     */
    public void showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);
        } else {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }
    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        // Have unused ad, no need to fetch another.
        if (isAdAvailable()) {
            return;
        }

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);

                AppOpenManager.this.appOpenAd = appOpenAd;
                AppOpenManager.this.loadTime = (new Date()).getTime();

                Log.d(LOG_TAG, "App-Open Ad Loaded");
            }

            /**
             * Called when an app open ad has failed to load.
             *
             * @param loadAdError the error.
             */
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error.
                super.onAdFailedToLoad(loadAdError);
            }

        };

//        AdRequest request = getAdRequest();
//        AppOpenAd.load(myApplication, AD_UNIT_ID, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

        AdRequest request = getAdRequest();
        if (KafaAds.getAds() != null) {
            Log.i(AppOpenManager.class.getSimpleName(), "fetchAd: AdsModel Not NUll : " + KafaAds.getAds().get_openapp());
            if (KafaAds.getAds().get_backup_status().equalsIgnoreCase("1")) {
                switch (KafaAds.getAds().get_backup_ads()) {
                    case "Applovin":
                        AppLovinSdk.initializeSdk(myApplication, new AppLovinSdk.SdkInitializationListener() {
                            @Override
                            public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                                MaxAppOpen maxAppOpen = new MaxAppOpen(myApplication, KafaAds.getAds().get_backup_openapp());
                            }
                        });
                        break;
                }
            } else {
                AppOpenAd.load(
                        myApplication, KafaAds.getAds().get_openapp(), request,
                        AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
            }
        } else {
            Log.i(AppOpenManager.class.getSimpleName(), "fetchAd: AdsModel NUll using default");
            AppOpenAd.load(
                    myApplication, AD_UNIT_ID, request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
        }

    }

    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /**
     * Utility method to check if ad was loaded more than n hours ago.
     */
    private boolean wasLoadTimeLessThanNHoursAgo() {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * (long) 4));
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        Log.d("OPENAPP", String.valueOf(appOpenAd));
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo();
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }
}
