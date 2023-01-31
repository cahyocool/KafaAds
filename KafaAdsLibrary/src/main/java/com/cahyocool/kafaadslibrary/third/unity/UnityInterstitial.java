package com.cahyocool.kafaadslibrary.third.unity;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.kafaads.kafaadslibrary.BuildConfig;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

public class UnityInterstitial implements IUnityAdsInitializationListener {
    private IUnityAdsLoadListener _loadListener;
    private IUnityAdsShowListener _showListener;
    private static final String TAG = UnityInterstitial.class.getSimpleName();
    private String intersKey;

    public UnityInterstitial(@NonNull String intersKey, com.cahyocool.kafaadslibrary.third.unity.IUnityAdsShowListener showListener) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "/UnityInterstitial");
        }
        this.intersKey = intersKey;
        _loadListener = new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String s) {
                UnityAds.show(KafaAds.activity, intersKey, new UnityAdsShowOptions(), _showListener);
            }

            @Override
            public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {

            }
        };
        _showListener = new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String s, UnityAds.UnityAdsShowError unityAdsShowError, String s1) {
                //showListener.onUnityAdsShowFailure(s, (U) unityAdsShowError, s1);
            }

            @Override
            public void onUnityAdsShowStart(String s) {
                if (showListener != null) {
                    showListener.onUnityAdsShowStart(s);
                }
            }

            @Override
            public void onUnityAdsShowClick(String s) {
                if (showListener != null) {
                    showListener.onUnityAdsShowClick(s);
                }
            }

            @Override
            public void onUnityAdsShowComplete(String s, UnityAds.UnityAdsShowCompletionState unityAdsShowCompletionState) {
                showListener.onUnityAdsShowComplete(s);
            }
        };
    }

    @Override
    public void onInitializationComplete() {
        showInterstitial();
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String s) {
        Log.e("UnityAdsExample", "Unity Ads initialization failed with error: [" + error + "] " + s);
    }

    public void showInterstitial() {
        UnityAds.load(intersKey, _loadListener);
    }
}
