package com.cahyocool.kafaadslibrary.third.unity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cahyocool.kafaadslibrary.KafaAds;
import com.kafaads.kafaadslibrary.BuildConfig;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

public class UnityReward implements IUnityAdsInitializationListener {
    private IUnityAdsLoadListener _loadListener;
    private com.unity3d.ads.IUnityAdsShowListener _showListener;
    private static final String TAG = UnityReward.class.getSimpleName();
    private String _rewardId;

    public UnityReward(@NonNull String rewardId, com.cahyocool.kafaadslibrary.third.unity.IUnityAdsShowListener showListener) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "/UnityReward");
        }
        this._rewardId = rewardId;
        _loadListener = new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String s) {
                UnityAds.show(KafaAds.activity, rewardId, new UnityAdsShowOptions(), _showListener);
            }

            @Override
            public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {

            }
        };
        _showListener = new com.unity3d.ads.IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String s, UnityAds.UnityAdsShowError unityAdsShowError, String s1) {

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
            public void onUnityAdsShowComplete(String s, UnityAds.UnityAdsShowCompletionState state) {
                if (state.equals(UnityAds.UnityAdsShowCompletionState.COMPLETED)) {
                    if (showListener != null) {
                        showListener.onUnityAdsShowComplete(s);
                    }
                }
            }
        };
    }

    @Override
    public void onInitializationComplete() {
        showReward();
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String s) {

    }

    public void showReward() {
        UnityAds.load(_rewardId, _loadListener);
    }
}
