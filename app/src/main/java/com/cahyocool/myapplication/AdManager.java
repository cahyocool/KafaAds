package com.cahyocool.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.OnInterstitialAdLoadListener;
import com.cahyocool.kafaadslibrary.OnMaxInterstitialAdLoadListener;
import com.cahyocool.kafaadslibrary.OnMaxRewardAdLoadListener;
import com.cahyocool.kafaadslibrary.OnRewardAdLoadListener;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.cahyocool.kafaadslibrary.data.AdName;
import com.cahyocool.kafaadslibrary.data.AdType;
import com.cahyocool.kafaadslibrary.third.admob.NativeTemplateStyle;
import com.cahyocool.kafaadslibrary.third.admob.TemplateView;
import com.cahyocool.kafaadslibrary.third.unity.IUnityAdsLoadListener;
import com.cahyocool.kafaadslibrary.third.unity.IUnityAdsShowListener;
import com.cahyocool.kafaadslibrary.third.unity.UnityAdsLoadError;
import com.cahyocool.kafaadslibrary.third.unity.UnityAdsShowCompletionState;
import com.cahyocool.kafaadslibrary.third.unity.UnityAdsShowError;

public class AdManager {
    private final Context context;
    private final Activity activity;
    private KafaAds ads;

    public AdManager(Activity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public void showBanner(boolean... halfBanner) {
        if (KafaAds.getAds().get_status().equalsIgnoreCase("1")) {
            if (KafaAds.getAds().get_backup_status().equalsIgnoreCase("1")) {
                switch (KafaAds.getAds().get_backup_ads()) {
                    case "Applovin":
//                        KafaAds.setMaxBannerCustom(true);
//                        KafaAds.setMaxBanner(ViewGroup.LayoutParams.MATCH_PARENT,
//                                activity.getResources().getDimensionPixelSize(com.kafaads.kafaadslibrary.R.dimen.banner_height),
//                                Gravity.TOP);
                        ads = new KafaAds.With(context)
                                .setContainer(activity.findViewById(R.id.lyt_banner))
                                .setAd(new Ad(AdName.APPLOVIN, AdType.BANNER, KafaAds.getAds().get_backup_banner()))
                                .build();
                        ads.load();
                        break;
                    case "Unity":
//                        ads = new KafaAds(context);
//                        ads.showUnityBanner(activity.findViewById(R.id.lyt_banner), new Ad(AdName.UNITY, AdType.BANNER, KafaAds.getAds().get_backup_banner()));
                        ads = new KafaAds.With(context)
                                .setContainer(activity.findViewById(R.id.lyt_banner))
                                .setAd(new Ad(AdName.UNITY, AdType.BANNER, KafaAds.getAds().get_backup_banner()))
                                .build();
                        ads.load();
                        break;
                }
            } else {
                AdType type;
                if (halfBanner.length > 0) {
                    type = AdType.HALF_BANNER;
                } else {
                    type = AdType.BANNER;
                }
                ads = new KafaAds.With(context)
                        .setContainer(activity.findViewById(R.id.lyt_banner))
                        .setAd(new Ad(AdName.ADMOB, type, KafaAds.getAds().get_banner()))
                        .build();
                ads.load();
            }
        }
    }

    public void showInterAds() {
        if (KafaAds.getAds().get_status().equalsIgnoreCase("1")) {
            if (KafaAds.getAds().get_backup_status().equals("1")) {
                switch (KafaAds.getAds().get_backup_ads()) {
                    case "Applovin":
                        Log.d("APPLOVIN-INTER-ID", KafaAds.getAds().get_backup_interstitial());
                        ads = new KafaAds.With(context)
                                .setAd(new Ad(AdName.APPLOVIN, AdType.INTERSTITIAL, KafaAds.getAds().get_backup_interstitial()))
                                .setOnMaxInterstitialAdLoadListener(new OnMaxInterstitialAdLoadListener() {
                                    @Override
                                    public void onAdExpanded() {

                                    }

                                    @Override
                                    public void onAdCollapsed() {

                                    }

                                    @Override
                                    public void onAdLoaded() {
                                        ads.showMaxInterstitial();
                                        Log.d("showInter", "iklan max inter muncul");
                                    }

                                    @Override
                                    public void onAdDisplayed() {

                                    }

                                    @Override
                                    public void onAdHidden() {
                                        Log.d("closedInter", "iklan max inter closed");
                                    }

                                    @Override
                                    public void onAdClicked() {

                                    }

                                    @Override
                                    public void onAdFailedToLoad() {

                                    }

                                    @Override
                                    public void onAdDisplayFailed() {

                                    }
                                })
                                .build();
                        ads.load();
                        break;
                    case "Unity":
                        IUnityAdsShowListener showListener;
                        showListener = new IUnityAdsShowListener() {
                            @Override
                            public void onUnityAdsShowFailure(String placementId, UnityAdsShowError unityAdsShowError, String message) {

                            }

                            @Override
                            public void onUnityAdsShowStart(String placementId) {
                                Log.d("showInter", "iklan inter muncul " + placementId);
                            }

                            @Override
                            public void onUnityAdsShowClick(String placementId) {
                                Log.d("showInter", "iklan inter klik " + placementId);
                            }

                            @Override
                            public void onUnityAdsShowComplete(String placementId) {
                                Log.d("showComplete", "iklan inter complete " + placementId);
                            }
                        };
                        ads = new KafaAds(context);
                        ads.unityShowInterstitial(KafaAds.getAds().get_backup_interstitial(), showListener);
                        break;
                }
            } else {
                Log.d("INTER-ID", KafaAds.getAds().get_interstitial());
                ads = new KafaAds.With(context)
                        .setAd(new Ad(AdName.ADMOB, AdType.INTERSTITIAL, KafaAds.getAds().get_interstitial()))
                        .setOnInterstitialAdLoadListener(new OnInterstitialAdLoadListener() {
                            @Override
                            public void onAdLoaded() {
                                ads.showInterstitial();
                                Log.d("showInter", "iklan inter muncul");
                            }

                            @Override
                            public void onAdFailedToLoad() {

                            }

                            @Override
                            public void onAdClosed() {
                                Log.d("closedInter", "iklan inter closed");
                            }
                        })
                        .build();
                ads.load();
            }
        }
    }

    public void showRewardAds() {
        if (KafaAds.getAds().get_status().equalsIgnoreCase("1")) {
            if (KafaAds.getAds().get_backup_status().equals("1")) {
                switch (KafaAds.getAds().get_backup_ads()) {
                    case "Applovin":
                        Log.d("APPLOVIN-REWARD-ID", KafaAds.getAds().get_backup_reward());
                        ads = new KafaAds.With(context)
                                .setAd(new Ad(AdName.APPLOVIN, AdType.REWARD, KafaAds.getAds().get_backup_reward()))
                                .setOnMaxRewardAdLoadListener(new OnMaxRewardAdLoadListener() {
                                    @Override
                                    public void onAdExpanded() {

                                    }

                                    @Override
                                    public void onAdCollapsed() {

                                    }

                                    @Override
                                    public void onAdLoaded() {
                                        ads.showMaxReward();
                                        Log.d("showReward", "iklan max reward muncul");
                                    }

                                    @Override
                                    public void onAdDisplayed() {

                                    }

                                    @Override
                                    public void onAdHidden() {
                                        Log.d("closedInter", "iklan max reward closed");
                                    }

                                    @Override
                                    public void onAdClicked() {

                                    }

                                    @Override
                                    public void onAdFailedToLoad() {

                                    }

                                    @Override
                                    public void onAdDisplayFailed() {

                                    }
                                })
                                .build();
                        ads.load();
                        break;
                    case "Unity":
                        IUnityAdsShowListener showListener;
                        showListener = new IUnityAdsShowListener() {
                            @Override
                            public void onUnityAdsShowFailure(String placementId, UnityAdsShowError unityAdsShowError, String message) {

                            }

                            @Override
                            public void onUnityAdsShowStart(String placementId) {
                                Log.d("showReward", "iklan rewa muncul " + placementId);
                            }

                            @Override
                            public void onUnityAdsShowClick(String placementId) {
                                Log.d("showClick", "iklan rewa klik " + placementId);
                            }

                            @Override
                            public void onUnityAdsShowComplete(String placementId) {
                                Log.d("showComplete", "iklan rewa complete " + placementId);
                            }
                        };
                        ads = new KafaAds(context);
                        ads.unityShowReward(KafaAds.getAds().get_backup_reward(), showListener);
                        break;
                }
            } else {
                Log.d("REWARD-ID", KafaAds.getAds().get_reward());
                //KafaAds.initAd(context, KafaAds.getAds().get_appid());
                ads = new KafaAds.With(context)
                        .setAd(new Ad(AdName.ADMOB, AdType.REWARD, KafaAds.getAds().get_reward()))
                        .setOnRewardAdLoadListener(new OnRewardAdLoadListener() {
                            @Override
                            public void onAdLoaded() {
                                ads.showReward();
                                Log.d("showRewa", "iklan rewa muncul");
                            }

                            @Override
                            public void onAdFailedToLoad() {

                            }

                            @Override
                            public void onAdClosed() {
                                Log.d("closedRewa", "iklan rewa closed");
                            }
                        })
                        .build();
                ads.load();
            }
        }
    }

    public void showNative() {
        ColorDrawable background = null;
        if (KafaAds.getAds().get_status().equalsIgnoreCase("1")) {
            if (KafaAds.getAds().get_backup_status().equals("1")) {
                switch (KafaAds.getAds().get_backup_ads()) {
                    case "Applovin":
                        FrameLayout frameLayout = activity.findViewById(R.id.max_native_ad);
                        frameLayout.setVisibility(View.VISIBLE);
                        ads = new KafaAds(context);
                        ads.showMaxNativeTemplate(frameLayout, KafaAds.getAds().get_backup_native());
                        break;
                }
            } else {
                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(null).build();
                TemplateView templateView = activity.findViewById(R.id.nativeTemplateView);
                templateView.setVisibility(View.VISIBLE);
                templateView.setStyles(styles);
                ads = new KafaAds(context);
                ads.showNative(AdName.ADMOB, KafaAds.getAds().get_native(), templateView);
            }
        }
    }
}
