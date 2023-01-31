package com.cahyocool.kafaadslibrary.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AdApi {
    @GET("{endUrl}")
    Call<List<Ad>> getAds(@Path(value = "endUrl", encoded = true) String endUrl);
}
