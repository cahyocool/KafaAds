package com.cahyocool.kafaadslibrary.third.applovin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applovin.mediation.MaxError;
import com.cahyocool.kafaadslibrary.data.Ad;

public interface OnAppLovinAdListener {
    void onAdExpanded(Ad ad);
    void onAdCollapsed(Ad ad);
    void onAdLoaded(@NonNull Ad ad, @Nullable Object data);
    void onAdDisplayed(Ad ad);
    void onAdHidden(Ad ad);
    void onAdClicked(Ad ad);
    void onAdFailedToLoad(Ad ad, MaxError error);
    void onAdDisplayFailed(Ad ad, MaxError error);
}
