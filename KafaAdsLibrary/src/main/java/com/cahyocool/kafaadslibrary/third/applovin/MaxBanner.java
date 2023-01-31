package com.cahyocool.kafaadslibrary.third.applovin;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.kafaads.kafaadslibrary.R;

public class MaxBanner extends BaseAppLovin {
    private MaxAdView maxAdView;
    private Context ctx;

    public MaxBanner(@NonNull Context context, @NonNull final Ad adx) {
        this.ctx = context;
        maxAdView = new MaxAdView(adx.key, KafaAds.activity);
        if (KafaAds.getMaxBannerCustom()) {
            maxAdView.setLayoutParams(KafaAds.getMaxBanner());
        } else {
            // Stretch to the width of the screen for banners to be fully functional
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            // Banner height on phones and tablets is 50 and 90, respectively
            int heightPx = KafaAds.activity.getResources().getDimensionPixelSize(R.dimen.banner_height);
            maxAdView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx, Gravity.BOTTOM));
        }
        maxAdView.setBackgroundColor(ContextCompat.getColor(KafaAds.activity, R.color.gnt_white));
        ViewGroup rootView = KafaAds.activity.findViewById(android.R.id.content);
        rootView.addView(maxAdView);
        maxAdView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                if (onAppLovinAdListener != null) {
                    onAppLovinAdListener.onAdLoaded(adx, maxAdView);
                }
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                if (onAppLovinAdListener != null) {
                    onAppLovinAdListener.onAdFailedToLoad(adx, error);
                }
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });
    }

    @Override
    public void loadPreparedAd() {
        maxAdView.loadAd();
    }
}
