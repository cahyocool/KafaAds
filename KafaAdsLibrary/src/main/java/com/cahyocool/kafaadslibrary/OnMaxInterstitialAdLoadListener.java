package com.cahyocool.kafaadslibrary;

public interface OnMaxInterstitialAdLoadListener {
    void onAdExpanded();
    void onAdCollapsed();
    void onAdLoaded();
    void onAdDisplayed();
    void onAdHidden();
    void onAdClicked();
    void onAdFailedToLoad();
    void onAdDisplayFailed();
}
