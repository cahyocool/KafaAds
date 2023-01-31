package com.cahyocool.kafaadslibrary.third.unity;

public interface IUnityAdsShowListener {
    void onUnityAdsShowFailure(String placementId, UnityAdsShowError unityAdsShowError, String message);
    void onUnityAdsShowStart(String placementId);
    void onUnityAdsShowClick(String placementId);
    void onUnityAdsShowComplete(String placementId);
}
