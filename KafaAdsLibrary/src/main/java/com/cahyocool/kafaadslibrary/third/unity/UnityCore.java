package com.cahyocool.kafaadslibrary.third.unity;

import android.content.Context;
import android.widget.FrameLayout;

import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.cahyocool.kafaadslibrary.data.AdType;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class UnityCore {
    private static final String TAG = UnityCore.class.getSimpleName();
    private Context context;
    BannerView view;

    public UnityCore(Context context) {
        this.context = context;
    }

    public void showBanner(FrameLayout layout, Ad ad) {
        if (ad.type == AdType.BANNER) {
            view = new BannerView(KafaAds.activity, ad.key, new UnityBannerSize(320,50));
        } else {
            view = new BannerView(KafaAds.activity, ad.key, new UnityBannerSize(720,90));
        }
        view.load();
        layout.removeAllViews();
        layout.addView(view);
    }
}
