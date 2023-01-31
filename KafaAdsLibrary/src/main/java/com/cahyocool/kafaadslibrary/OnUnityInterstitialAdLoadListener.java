package com.cahyocool.kafaadslibrary;

import com.unity3d.ads.UnityAds;

public interface OnUnityInterstitialAdLoadListener {
    void onUnityAdsAdLoaded();
    void onUnityAdsShowFailure();
    void onUnityAdsShowStart();
    void onUnityAdsShowClick();
    void onUnityAdsShowComplete();
}
