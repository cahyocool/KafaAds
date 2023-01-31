package com.cahyocool.kafaadslibrary.data;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    private static final String TAG = ApiClient.class.getSimpleName();

    private ApiClient(String url) {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
    }

    static Retrofit getClient(String url) {
        if (retrofit == null) {
            new ApiClient(url);
        }

        return retrofit;
    }
}
