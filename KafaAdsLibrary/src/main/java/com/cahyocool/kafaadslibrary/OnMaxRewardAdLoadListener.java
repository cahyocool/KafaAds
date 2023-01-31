package com.cahyocool.kafaadslibrary;

public interface OnMaxRewardAdLoadListener {
    void onAdExpanded();
    void onAdCollapsed();
    void onAdLoaded();
    void onAdDisplayed();
    void onAdHidden();
    void onAdClicked();
    void onAdFailedToLoad();
    void onAdDisplayFailed();
}
