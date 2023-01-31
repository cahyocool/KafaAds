package com.cahyocool.kafaadslibrary.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kafaads.kafaadslibrary.BuildConfig;
import com.cahyocool.kafaadslibrary.KafaAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdDataRepository implements AdDataSource {
    private AdApi adApi;
    private String endUrl;
    private List<Ad> adCache;
    private int position;
    private static final String TAG = AdDataRepository.class.getSimpleName();

    public AdDataRepository(@Nullable String baseUrl) {
        if (baseUrl != null) {
            String[] splits = baseUrl.split("/");
            endUrl = splits[splits.length - 1];
            adApi = ApiClient.getClient(baseUrl.substring(0, baseUrl.indexOf(endUrl))).create(AdApi.class);
        }
        adCache = new ArrayList<>();
        position = 0;
    }

    public void add(@NonNull Ad ad) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "/add" +
                    "\nad type : " + ad.type +
                    "\nad name : " + ad.name);
        }

        adCache.add(ad);
    }

    @Override
    public void getAll(@NonNull final OnGetAllSuccessListener onGetAllSuccessListener,
                       @NonNull final OnGetAllFailureListener onGetAllFailureListener) {

        final long startTime = System.currentTimeMillis();
        if (adApi != null) {
            adApi.getAds(endUrl).enqueue(new Callback<List<Ad>>() {
                @Override
                public void onResponse(@NonNull Call<List<Ad>> call,
                                       @NonNull Response<List<Ad>> response) {
                    List<Ad> adList = response.body();

                    long endTime = System.currentTimeMillis();
                    Log.d(KafaAds.TAG, "#0 [ RESPONSE_URL_SUCCESS ] - CURRENT_TIME_MILLIS: " + String.valueOf((endTime - startTime)/1000)+"초");

                    if (adList != null) {
                        Collections.reverse(adList);

                        for (Ad ad : adList) {
                            adCache.add(0, ad);
                        }
                        onGetAllSuccessListener.onSuccess(adCache);
                    } else if (!adCache.isEmpty()) {
                        onGetAllSuccessListener.onSuccess(adCache);
                    } else {
                        onGetAllFailureListener.onFailure();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Ad>> call,
                                      @NonNull Throwable t) {
                    if (call.isExecuted() &&
                            !call.isCanceled()) {
                        call.cancel();
                    }

                    if(KafaAds.TEST) {
                        int endTime = (int) (System.currentTimeMillis() / 1000);
                        Log.d(KafaAds.TAG, "#0 [ RESPONSE_URL_FAILURE ] - CURRENT_TIME_MILLIS: " + String.valueOf((endTime - startTime)/1000)+"초");
                    }

                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "//onFailure");
                    }

                    if (!adCache.isEmpty()) {
                        onGetAllSuccessListener.onSuccess(adCache);
                    } else {
                        onGetAllFailureListener.onFailure();
                    }
                }
            });
        } else if (!adCache.isEmpty()) {
            onGetAllSuccessListener.onSuccess(adCache);
        } else {
            onGetAllFailureListener.onFailure();
        }
    }

    @Override
    public void next(@NonNull OnNextSuccessListener onNextSuccessListener,
                     @NonNull OnNextFailureListener onNextFailureListener) {
        if (!adCache.isEmpty()) {
            onNextSuccessListener.onSuccess(adCache.get(position++ % adCache.size()));
        } else {
            onNextFailureListener.onFailure();
        }
    }
}
