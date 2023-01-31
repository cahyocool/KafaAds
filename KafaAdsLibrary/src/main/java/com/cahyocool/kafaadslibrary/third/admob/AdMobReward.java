package com.cahyocool.kafaadslibrary.third.admob;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.kafaads.kafaadslibrary.BuildConfig;

public class AdMobReward extends BaseRewardThirdParty {
    private RewardedAd mRewardedAd;
    private static final String TAG = AdMobReward.class.getSimpleName();
    private long startTime, endTime;
    private Ad ad;
    private Context context;

    public AdMobReward(@NonNull Context context, @NonNull final Ad ad) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "/AdMobReward");
        }

        this.ad = ad;
        this.context = context;
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(KafaAds.activity, ad.key,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.toString());
                        mRewardedAd = null;
                        endTime = System.currentTimeMillis();
                        if (onAdLoadListener != null) {
                            onAdLoadListener.onAdFailedToLoad(ad);
                        }
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                        endTime = System.currentTimeMillis();
                        if (onAdLoadListener != null) {
                            onAdLoadListener.onAdLoaded(ad, null);
                        }
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                mRewardedAd = null;
                                endTime = System.currentTimeMillis();
                                if (onAdLoadListener != null) {
                                    onAdLoadListener.onAdClosed(ad);
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                mRewardedAd = null;
                                endTime = System.currentTimeMillis();
                                if (onAdLoadListener != null) {
                                    onAdLoadListener.onAdFailedToLoad(ad);
                                }
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });
                    }
                });
    }

    @Override
    public void loadPreparedAd() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void showPreparedAd() {
        mRewardedAd.show(KafaAds.activity, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                // Handle the reward.
                Log.d(TAG, "The user earned the reward.");
            }
        });
    }
}
