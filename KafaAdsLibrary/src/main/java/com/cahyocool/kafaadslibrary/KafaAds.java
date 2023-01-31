package com.cahyocool.kafaadslibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.adPlacer.MaxAdPlacer;
import com.applovin.mediation.nativeAds.adPlacer.MaxAdPlacerSettings;
import com.applovin.mediation.nativeAds.adPlacer.MaxRecyclerAdapter;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.cahyocool.kafaadslibrary.data.AdDataRepository;
import com.cahyocool.kafaadslibrary.data.AdDataSource;
import com.cahyocool.kafaadslibrary.data.AdName;
import com.cahyocool.kafaadslibrary.data.AdType;
import com.cahyocool.kafaadslibrary.models.KafaAppSetting;
import com.cahyocool.kafaadslibrary.models.KafaModel;
import com.cahyocool.kafaadslibrary.third.applovin.BaseAppLovinInterstitial;
import com.cahyocool.kafaadslibrary.third.applovin.BaseAppLovinReward;
import com.cahyocool.kafaadslibrary.third.admob.BaseInterstitialThirdParty;
import com.cahyocool.kafaadslibrary.third.admob.BaseRewardThirdParty;
import com.cahyocool.kafaadslibrary.third.admob.OnAdLoadListener;
import com.cahyocool.kafaadslibrary.third.applovin.OnAppLovinAdListener;
import com.cahyocool.kafaadslibrary.third.unity.IUnityAdsShowListener;
import com.cahyocool.kafaadslibrary.third.unity.OnUnityAdListener;
import com.cahyocool.kafaadslibrary.third.admob.AdMobBanner;
import com.cahyocool.kafaadslibrary.third.admob.AdMobInterstitial;
import com.cahyocool.kafaadslibrary.third.admob.AdMobReward;
import com.cahyocool.kafaadslibrary.third.admob.AppOpenManager;
import com.cahyocool.kafaadslibrary.third.admob.TemplateView;
import com.cahyocool.kafaadslibrary.third.applovin.MaxAppOpen;
import com.cahyocool.kafaadslibrary.third.applovin.MaxBanner;
import com.cahyocool.kafaadslibrary.third.applovin.MaxInterstitial;
import com.cahyocool.kafaadslibrary.third.applovin.MaxRewards;
import com.cahyocool.kafaadslibrary.third.facebook.FacebookBanner;
import com.cahyocool.kafaadslibrary.third.facebook.FacebookInterstitial;
import com.cahyocool.kafaadslibrary.third.facebook.FacebookNative;
import com.cahyocool.kafaadslibrary.third.unity.UnityBanner;
import com.cahyocool.kafaadslibrary.third.unity.UnityCore;
import com.cahyocool.kafaadslibrary.third.unity.UnityInterstitial;
import com.cahyocool.kafaadslibrary.third.unity.UnityReward;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.kafaads.kafaadslibrary.BuildConfig;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import kotlin.Unit;

public class KafaAds implements OnAdLoadListener, OnAppLovinAdListener, OnUnityAdListener {
    private Context context;
    public static Activity activity;
    private AdDataRepository adDataRepository;
    private FrameLayout container;
    private Timer mTimer;
    private int interstitialCount;
    private OnInterstitialAdLoadListener onInterstitialAdLoadListener;
    private OnRewardAdLoadListener onRewardAdLoadListener;
    private OnMaxInterstitialAdLoadListener onMaxInterstitialAdLoadListener;
    private OnMaxRewardAdLoadListener onMaxRewardAdLoadListener;
    private LoadTimerTask mTimerTask;
    private AdMobBanner adMobBanner;
    private AdMobInterstitial adMobInterstitial;
    private AdMobReward adMobReward;
    private FacebookBanner facebookBanner;
    private FacebookNative facebookNative;
    private FacebookInterstitial facebookInterstitial;
    private MaxBanner maxBanner;
    private MaxRewards maxRewards;
    private MaxInterstitial maxInterstitial;
    private UnityBanner unityBanner;
    private LoadHandler mHandler;
    private Runnable mRunnable;
    private BaseInterstitialThirdParty currentBaseInterstitialThirdParty;
    private BaseRewardThirdParty currentBaseRewardThirdParty;
    private BaseAppLovinInterstitial currentAppLovinInterstitial;
    private BaseAppLovinReward currentBaseAppLovinReward;
    private static final int FACEBOOK = 2;
    private static final int ADMOB = 3;
    private static final int APPLOVIN = 4;
    private static final int UNITY = 5;
    private static final int HOUSE = 13;
    private static final int BANNER = 101;
    private static final int HALF_BANNER = 103;
    private static final int INTERSTITIAL = 107;
    private static final int REWARD = 110;
    private static final int NATIVE = 109;
    private static final int ENDING = 113;
    private static final int DEFAULT_DURATION_TIME = 3;
    public static final String TAG = "KafaAdsLibraryTest";
    public static Boolean TEST = false;
    private static KafaModel kafaModel;
    private static KafaAppSetting kafaAppSetting;

    public static void setAds(KafaModel kafaModel) {
        KafaAds.kafaModel = kafaModel;
    }

    public static KafaModel getAds() {
        return kafaModel;
    }

    public static void setSetting(KafaAppSetting kafaAppSetting) {
        KafaAds.kafaAppSetting = kafaAppSetting;
    }

    public static KafaAppSetting getSetting() {
        return kafaAppSetting;
    }

    public static Activity getActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            } else {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }

    public static void initAd(Activity activity) {
        KafaAds.activity = activity;
        if (KafaAds.getAds().get_backup_status().equalsIgnoreCase("1")) {
            switch (KafaAds.getAds().get_backup_ads()) {
                case "Applovin":
                    AppLovinSdk.getInstance(activity).setMediationProvider("max");
                    AppLovinSdk.initializeSdk(activity, new AppLovinSdk.SdkInitializationListener() {
                        @Override
                        public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                        }
                    });
                    break;
                case "Unity":
                    UnityAds.initialize(activity, KafaAds.getAds().get_backup_appid(), KafaAds.getAds().get_unity_test_mode());
                    break;
                case "FBAds":
                    break;
            }
        } else {
            MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
        }

    }

    public static String adsVersion(AdName adName) {
        String res = null;
        switch (adName) {
            case ADMOB:
                res = String.valueOf(MobileAds.getVersion());
                break;
        }

        return res;
    }

    public static void initOpenAd(Application app) {
        if (KafaAds.getAds().get_backup_status().equalsIgnoreCase("1")) {
            switch (KafaAds.getAds().get_backup_ads()) {
                case "Applovin":
                    AppLovinSdk.initializeSdk(app.getApplicationContext(), new AppLovinSdk.SdkInitializationListener() {
                        @Override
                        public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                            MaxAppOpen maxAppOpen = new MaxAppOpen(app.getApplicationContext(), KafaAds.getAds().get_backup_openapp());
                        }
                    });
                    break;
            }
        } else {
            AppOpenManager appOpenManager = new AppOpenManager(app);
        }

    }

    public KafaAds(Context context) {
        context = context;
    }

    private KafaAds(With with) {
        context = with.context;
        adDataRepository = with.adDataRepository;
        container = with.container;
        mTimer = new Timer();
        interstitialCount = with.interstitialCount;
        onInterstitialAdLoadListener = with.onInterstitialAdLoadListener;
        onRewardAdLoadListener = with.onRewardAdLoadListener;
        onMaxInterstitialAdLoadListener = with.onMaxInterstitialAdLoadListener;
        onMaxRewardAdLoadListener = with.onMaxRewardAdLoadListener;
        TEST = with.test_option;

        adDataRepository.getAll(new AdDataSource.OnGetAllSuccessListener() {
            @Override
            public void onSuccess(@NonNull List<Ad> adList) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "//onSuccess");
                }
                mHandler = new LoadHandler(Looper.getMainLooper());
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mHandler.handleMessage(new Message());
                    }
                };
            }
        }, new AdDataSource.OnGetAllFailureListener() {
            @Override
            public void onFailure() {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "//onFailure");
                }
                mHandler = new LoadHandler(Looper.getMainLooper());
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mHandler.handleMessage(new Message());
                    }
                };
            }
        });
    }

    @Override
    public void onAdExpanded(Ad ad) {

    }

    @Override
    public void onAdCollapsed(Ad ad) {

    }

    @Override
    public void onAdLoaded(@NonNull Ad ad,
                           @Nullable Object data) {
        switch (ad.name.getCode() * ad.type.getCode()) {
            case ADMOB * BANNER:
            case ADMOB * HALF_BANNER: {
                com.google.android.gms.ads.AdView adView =
                        (com.google.android.gms.ads.AdView) data;

                container.removeAllViews();
                container.addView(adView);
                break;
            }
            case ADMOB * INTERSTITIAL:
                currentBaseInterstitialThirdParty = adMobInterstitial;

                if (onInterstitialAdLoadListener != null) {
                    onInterstitialAdLoadListener.onAdLoaded();
                }
                break;
            case ADMOB * REWARD:
                currentBaseRewardThirdParty = adMobReward;

                if (onRewardAdLoadListener != null) {
                    onRewardAdLoadListener.onAdLoaded();
                }
                break;
            case APPLOVIN * BANNER:
            case APPLOVIN * HALF_BANNER: {
                com.applovin.adview.AppLovinAdView adView =
                        (com.applovin.adview.AppLovinAdView) data;

                container.removeAllViews();
                container.addView(adView);
                break;
            }
            case APPLOVIN * INTERSTITIAL:
                currentAppLovinInterstitial = maxInterstitial;

                if (onMaxInterstitialAdLoadListener != null) {
                    onMaxInterstitialAdLoadListener.onAdLoaded();
                }
                break;
            case APPLOVIN * REWARD:
                currentBaseAppLovinReward = maxRewards;

                if (onMaxRewardAdLoadListener != null) {
                    onMaxRewardAdLoadListener.onAdLoaded();
                }
                break;
            case UNITY * BANNER:
            case UNITY * HALF_BANNER: {
                com.unity3d.services.banners.BannerView adView =
                        (com.unity3d.services.banners.BannerView) data;

                container.removeAllViews();
                container.addView(adView);
                break;
            }
            case FACEBOOK * INTERSTITIAL:
                currentBaseInterstitialThirdParty = facebookInterstitial;

                if (onInterstitialAdLoadListener != null) {
                    onInterstitialAdLoadListener.onAdLoaded();
                }
                break;
            case FACEBOOK * BANNER:
            case FACEBOOK * HALF_BANNER: {
                com.facebook.ads.AdView adView = (com.facebook.ads.AdView) data;

                container.removeAllViews();
                container.addView(adView);
                break;
            }
            default:
                onAdFailedToLoad(ad);
                break;
        }
    }

    @Override
    public void onAdDisplayed(Ad ad) {

    }

    @Override
    public void onAdHidden(Ad ad) {
        if (ad.type == AdType.INTERSTITIAL &&
                --interstitialCount <= 0) {
            if (onMaxInterstitialAdLoadListener != null) {
                onMaxInterstitialAdLoadListener.onAdHidden();
            }
        } else if (ad.type == AdType.REWARD) {
            if (onMaxRewardAdLoadListener != null) {
                onMaxRewardAdLoadListener.onAdHidden();
            }
        }
    }

    @Override
    public void onAdClicked(Ad ad) {

    }

    @Override
    public void onAdFailedToLoad(Ad ad, MaxError error) {
        if (ad.type == AdType.INTERSTITIAL &&
                --interstitialCount <= 0) {
            if (onMaxInterstitialAdLoadListener != null) {
                onMaxInterstitialAdLoadListener.onAdFailedToLoad();
            }
        } else if (ad.type == AdType.REWARD) {
            if (onMaxRewardAdLoadListener != null) {
                onMaxRewardAdLoadListener.onAdFailedToLoad();
            }
        } else {
            adDataRepository.next(new AdDataSource.OnNextSuccessListener() {
                @Override
                public void onSuccess(@NonNull Ad ad) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "/onAdFailedToLoad//onSuccess" +
                                "\nad type : " + ad.type +
                                "\nad name : " + ad.name);
                    }

                    load(ad);
                }
            }, new AdDataSource.OnNextFailureListener() {
                @Override
                public void onFailure() {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "/onAdFailedToLoad//onFailure");
                    }
                }
            });
        }
    }

    @Override
    public void onAdDisplayFailed(Ad ad, MaxError error) {

    }

    @Override
    public void onAdFailedToLoad(Ad ad) {
        if (ad.type == AdType.INTERSTITIAL &&
                --interstitialCount <= 0) {
            if (onInterstitialAdLoadListener != null) {
                onInterstitialAdLoadListener.onAdFailedToLoad();
            }
        } else if (ad.type == AdType.REWARD) {
            if (onRewardAdLoadListener != null) {
                onRewardAdLoadListener.onAdFailedToLoad();
            }
        } else {
            adDataRepository.next(new AdDataSource.OnNextSuccessListener() {
                @Override
                public void onSuccess(@NonNull Ad ad) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "/onAdFailedToLoad//onSuccess" +
                                "\nad type : " + ad.type +
                                "\nad name : " + ad.name);
                    }

                    load(ad);
                }
            }, new AdDataSource.OnNextFailureListener() {
                @Override
                public void onFailure() {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "/onAdFailedToLoad//onFailure");
                    }
                }
            });
        }
    }

    @Override
    public void onAdClosed(Ad ad) {
        if (ad.type == AdType.INTERSTITIAL &&
                --interstitialCount <= 0) {
            if (onInterstitialAdLoadListener != null) {
                onInterstitialAdLoadListener.onAdClosed();
            }
        } else if (ad.type == AdType.REWARD) {
            if (onRewardAdLoadListener != null) {
                onRewardAdLoadListener.onAdClosed();
            }
        }
    }

    public void load() {
        if (mTimerTask == null) {
            mTimerTask = new LoadTimerTask() {
                @Override
                public void run() {
                    super.run();
                    if (mHandler != null &&
                            mRunnable != null) {
                        mHandler.post(mRunnable);
                        mTimerTask.cancel();
                    }
                }
            };
        }

        if (mTimerTask.isRun()) {
            mTimerTask.cancel();
            mTimerTask = new LoadTimerTask() {
                @Override
                public void run() {
                    super.run();
                    if (mHandler != null &&
                            mRunnable != null) {

                        mHandler.post(mRunnable);
                        mTimerTask.cancel();
                    }
                }
            };
        }

        mTimer.schedule(mTimerTask, 0, TimeUnit.SECONDS.toMillis(DEFAULT_DURATION_TIME));
    }

    private void load(Ad ad) {
        //admob
        switch (ad.name.getCode() * ad.type.getCode()) {
            case ADMOB * BANNER:
            case ADMOB * HALF_BANNER:
                if (adMobBanner == null) {
                    adMobBanner = new AdMobBanner(context, ad);

                    adMobBanner.setOnAdLoadListener(this);
                }

                adMobBanner.loadPreparedAd();
                break;
            case ADMOB * INTERSTITIAL:
                if (adMobInterstitial == null) {
                    adMobInterstitial = new AdMobInterstitial(context, ad);

                    adMobInterstitial.setOnAdLoadListener(this);
                }

                adMobInterstitial.loadPreparedAd();
                break;
            case ADMOB * REWARD:
                if (adMobReward == null) {
                    adMobReward = new AdMobReward(context, ad);

                    adMobReward.setOnAdLoadListener(this);
                }

                adMobReward.loadPreparedAd();
                break;
            case APPLOVIN * BANNER:
            case APPLOVIN * HALF_BANNER:
                if (maxBanner == null) {
                    maxBanner = new MaxBanner(context, ad);

                    maxBanner.setOnAppLovinAdListener(this);
                }

                maxBanner.loadPreparedAd();
                break;
            case APPLOVIN * INTERSTITIAL:
                if (maxInterstitial == null) {
                    maxInterstitial = new MaxInterstitial(context, ad);

                    maxInterstitial.setOnAppLovinAdListener(this);
                }
                maxInterstitial.loadPreparedAd();
                break;
            case APPLOVIN * REWARD:
                if (maxRewards == null) {
                    maxRewards = new MaxRewards(context, ad);

                    maxRewards.setOnAppLovinAdListener(this);
                }
                maxRewards.loadPreparedAd();
                break;
            case UNITY * BANNER:
            case UNITY * HALF_BANNER:
                if (unityBanner == null) {
                    unityBanner = new UnityBanner(context, ad);

                    unityBanner.setOnUnityAdListener(this);
                }

                unityBanner.loadPreparedAd();
                break;
            case FACEBOOK * BANNER:
            case FACEBOOK * HALF_BANNER:
                if (facebookBanner == null) {
                    facebookBanner = new FacebookBanner(context, ad);

                    facebookBanner.setOnAdLoadListener(this);
                }

                facebookBanner.loadPreparedAd();
                break;
            case FACEBOOK * INTERSTITIAL:
                if (facebookInterstitial == null) {
                    facebookInterstitial = new FacebookInterstitial(context, ad);

                    facebookInterstitial.setOnAdLoadListener(this);
                }

                facebookInterstitial.loadPreparedAd();
                break;
            case FACEBOOK * NATIVE:
                if (facebookNative == null) {
                    facebookNative = new FacebookNative(context, ad);

                    facebookNative.setOnAdLoadListener(this);
                }

                facebookNative.loadPreparedAd();
                break;
        }
    }

    // ========================== ADMOB ==================================================
    public void showInterstitial() {
        if (currentBaseInterstitialThirdParty != null) {
            currentBaseInterstitialThirdParty.showPreparedAd();
        }
    }

    public void showReward() {
        if (currentBaseRewardThirdParty != null) {
            currentBaseRewardThirdParty.showPreparedAd();
        }
    }

    public void showNative(AdName adName, String nativeId, TemplateView templateView) {
        switch (adName) {
            case ADMOB:
                AdLoader adLoader = new AdLoader.Builder(KafaAds.activity, nativeId)
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                templateView.setNativeAd(nativeAd);
                            }
                        })
                        .withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(LoadAdError adError) {
                                Log.d("onAdFailed", "type: [ ADMOB ], kind: [ NATIVE ], errorCode: " + adError);
                            }
                        })
                        .withNativeAdOptions(new NativeAdOptions.Builder()
                                // Methods in the NativeAdOptions.Builder class can be
                                // used here to specify individual options settings.
                                .build())
                        .build();
                adLoader.loadAd(new AdRequest.Builder().build());
                break;
        }
    }

    // ========================== END ADMOB ==================================================
    // ========================== APPLOVIN ===================================================
    private static boolean _check;
    private static FrameLayout.LayoutParams _maxParams;
    private static int _width, _height, _position;

    public static void setMaxBannerCustom(boolean check) {
        _check = check;
    }

    public static Boolean getMaxBannerCustom() {
        return _check;
    }

    public static void setMaxBanner(int width, int height, int position) {
        _width = width;
        _height = height;
        _position = position;
    }

    public static FrameLayout.LayoutParams getMaxBanner() {
        return new FrameLayout.LayoutParams(_width, _height, _position);
    }

    public void showMaxInterstitial() {
        if (currentAppLovinInterstitial != null) {
            currentAppLovinInterstitial.showPreparedAd();
        }
    }

    public void showMaxReward() {
        if (currentBaseAppLovinReward != null) {
            currentBaseAppLovinReward.showPreparedAd();
        }
    }

    public void showMaxNativeTemplate(FrameLayout nativeAdContainer, String nativeId) {
        MaxNativeAdLoader nativeAdLoader;
        final MaxAd[] nativeAd = {null};

        nativeAdLoader = new MaxNativeAdLoader(nativeId, KafaAds.activity);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if (nativeAd[0] != null) {
                    nativeAdLoader.destroy(nativeAd[0]);
                }
                // Save ad for cleanup.
                nativeAd[0] = ad;
                // Add ad view to view.
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView(nativeAdView);
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad) {
                // Optional click callback
            }
        });
        nativeAdLoader.loadAd();
    }

    public void showMaxNativePlacer(RecyclerView recyclerView, String nativeId, int fixedPos, int fixedPos2, int interval) {
        MaxRecyclerAdapter adAdapter;
        RecyclerView.Adapter adapter;

        adapter = recyclerView.getAdapter();
        MaxAdPlacerSettings settings = new MaxAdPlacerSettings(nativeId);
        settings.addFixedPosition(fixedPos);
        settings.addFixedPosition(fixedPos2);
        settings.setRepeatingInterval(interval);

        adAdapter = new MaxRecyclerAdapter(settings, adapter, KafaAds.activity);
        adAdapter.setListener(new MaxAdPlacer.Listener() {
            @Override
            public void onAdLoaded(final int position) {
            }

            @Override
            public void onAdRemoved(final int position) {
            }

            @Override
            public void onAdClicked(final MaxAd ad) {
            }

            @Override
            public void onAdRevenuePaid(final MaxAd ad) {
            }
        });
        recyclerView.setAdapter(adAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(KafaAds.activity));

        adAdapter.loadAds();
    }

    // ========================== END ADMOB ==================================================
    // ========================== Unity ======================================================
    public void unityShowInterstitial(String intersID, IUnityAdsShowListener showListener) {
        UnityInterstitial interstitial = new UnityInterstitial(intersID, showListener);
        interstitial.showInterstitial();
    }

    public void unityShowReward(String rewardId, IUnityAdsShowListener showListener) {
        UnityReward reward = new UnityReward(rewardId, showListener);
        reward.showReward();
    }

    public void showUnityBanner(FrameLayout layout, Ad ad) {
        UnityCore core = new UnityCore(context);
        core.showBanner(layout, ad);
    }
    // ========================== END Unity ==================================================

    public void close() {
        if (mTimerTask != null
                && mTimerTask.isRun()) {
            mTimerTask.cancel();
        }

        mTimerTask = null;
    }

    public static class With {
        private Context context;
        private List<Ad> adList;
        private FrameLayout container;
        private AdDataRepository adDataRepository;
        private int interstitialCount;
        private OnInterstitialAdLoadListener onInterstitialAdLoadListener;
        private OnRewardAdLoadListener onRewardAdLoadListener;
        private OnMaxInterstitialAdLoadListener onMaxInterstitialAdLoadListener;
        private OnMaxRewardAdLoadListener onMaxRewardAdLoadListener;
        private Boolean test_option = false;

        public With(@NonNull Context context) {
            this.context = context;
            adList = new ArrayList<>();
            interstitialCount = 0;
        }

        public With setBaseUrl(@Nullable String baseUrl) {
            adDataRepository = new AdDataRepository(baseUrl);
            return this;
        }

        public With setAd(@NonNull Ad ad) {
            adList.add(ad);

            return this;
        }

        public With setContainer(@NonNull FrameLayout container) {
            this.container = container;

            return this;
        }

        public With setOnInterstitialAdLoadListener(OnInterstitialAdLoadListener onInterstitialAdLoadListener) {
            this.onInterstitialAdLoadListener = onInterstitialAdLoadListener;

            return this;
        }

        public With setOnRewardAdLoadListener(OnRewardAdLoadListener onRewardAdLoadListener) {
            this.onRewardAdLoadListener = onRewardAdLoadListener;

            return this;
        }

        public With setOnMaxInterstitialAdLoadListener(OnMaxInterstitialAdLoadListener onMaxInterstitialAdLoadListener) {
            this.onMaxInterstitialAdLoadListener = onMaxInterstitialAdLoadListener;

            return this;
        }

        public With setOnMaxRewardAdLoadListener(OnMaxRewardAdLoadListener onMaxRewardAdLoadListener) {
            this.onMaxRewardAdLoadListener = onMaxRewardAdLoadListener;

            return this;
        }

        public With setAdmangerTest(Boolean flag) {
            this.test_option = flag;
            return this;
        }

        public KafaAds build() {
            boolean valid = true;
            for (Ad ad : adList) {
                if ((ad.type == AdType.BANNER ||
                        ad.type == AdType.NATIVE) &&
                        container == null) {
                    valid = false;
                    break;
                }

                if (ad.type == AdType.INTERSTITIAL &&
                        container != null) {
                    valid = false;
                    break;
                } else {
                    interstitialCount++;
                }

                if (adDataRepository == null) {
                    adDataRepository = new AdDataRepository(null);
                }

                adDataRepository.add(ad);
            }
            if (adDataRepository == null) {
                valid = false;
            }
            return valid ? new KafaAds(this) : null;
        }
    }

    class LoadTimerTask extends TimerTask {
        private boolean run = false;

        @Override
        public void run() {
            run = true;
        }

        @Override
        public boolean cancel() {
            run = false;

            return super.cancel();
        }

        boolean isRun() {
            return run;
        }
    }

    class LoadHandler extends Handler {
        private final WeakReference<Looper> looper;

        LoadHandler(Looper looper) {
            this.looper = new WeakReference<>(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            adDataRepository.next(new AdDataSource.OnNextSuccessListener() {
                @Override
                public void onSuccess(@NonNull Ad ad) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "//handleMessage//onSuccess" +
                                "\nad type : " + ad.type +
                                "\nad name : " + ad.name);
                    }
                    load(ad);
                }
            }, new AdDataSource.OnNextFailureListener() {
                @Override
                public void onFailure() {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "//handleMessage//onFailure");
                    }
                    onAdFailedToLoad(null);
                }
            });
        }
    }

    public static String getServer() {
        String base_url;
        switch (KafaAds.getSetting().get_baseServer()) {
            case "ONEDRIVE":
                base_url = "https://onedrive.live.com/download?cid=";
                break;
            case "DROPBOX":
                base_url = "https://dl.dropboxusercontent.com/s/";
                break;
            case "GDRIVE":
                base_url = "https://drive.google.com/uc?export=download&id=";
                break;
            case "SELFHOST":
            case "":
                base_url = "";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + KafaAds.getSetting().get_baseServer());
        }

        return base_url;
    }
}
