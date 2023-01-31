package com.cahyocool.kafaadslibrary.third.unity;

public interface IUnityAdsLoadListener {
    void onUnityAdsAdLoaded(String s);
    void onUnityAdsFailedToLoad(String s, UnityAdsLoadError unityAdsLoadError, String s1);
}
