package com.cahyocool.myapplication;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.cahyocool.kafaadslibrary.KafaAds;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        //KafaAds.initOpenAd(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
