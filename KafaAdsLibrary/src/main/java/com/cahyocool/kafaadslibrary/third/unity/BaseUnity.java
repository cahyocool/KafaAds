package com.cahyocool.kafaadslibrary.third.unity;

public abstract class BaseUnity {
    public OnUnityAdListener onUnityAdListener;

    public void setOnUnityAdListener(OnUnityAdListener onUnityAdListener) {
        this.onUnityAdListener = onUnityAdListener;
    }

    public abstract void loadPreparedAd();
}
