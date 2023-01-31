package com.cahyocool.kafaadslibrary.third.admob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cahyocool.kafaadslibrary.data.Ad;

public interface OnAdLoadListener {
    void onAdLoaded(@NonNull Ad ad,
                    @Nullable Object data);

    void onAdFailedToLoad(Ad ad);
    void onAdClosed(Ad ad);
}
