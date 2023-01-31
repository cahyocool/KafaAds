package com.cahyocool.kafaadslibrary.third.unity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cahyocool.kafaadslibrary.data.Ad;

public interface OnUnityAdListener {
    void onAdLoaded(@NonNull Ad ad,
                    @Nullable Object data);

    void onAdFailedToLoad(Ad ad);
    void onAdClosed(Ad ad);
}
