package com.cahyocool.kafaadslibrary.third.unity;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.data.Ad;
import com.kafaads.kafaadslibrary.R;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class UnityBanner extends BaseUnity {
    BannerView view;
    private static final String TAG = UnityBanner.class.getSimpleName();
    private long startTime, endTime;
    private Context ctx;

    public UnityBanner(@NonNull Context context, @NonNull final Ad ad) {
        view = new BannerView(KafaAds.activity, ad.key, new UnityBannerSize(320,50));
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = KafaAds.activity.getResources().getDimensionPixelSize(R.dimen.banner_height);
        view.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx, Gravity.BOTTOM));
        ViewGroup rootView = KafaAds.activity.findViewById(android.R.id.content);
        rootView.addView(view);
    }

    @Override
    public void loadPreparedAd() {
        startTime = System.currentTimeMillis();
        view.load();
    }
}
