package com.cahyocool.kafaadslibrary.data;

import androidx.annotation.NonNull;

import java.util.List;

public interface AdDataSource {
    @FunctionalInterface
    interface OnGetAllSuccessListener {
        void onSuccess(@NonNull List<Ad> adList);
    }

    @FunctionalInterface
    interface OnGetAllFailureListener {
        void onFailure();
    }

    void getAll(@NonNull OnGetAllSuccessListener onGetAllSuccessListener,
                @NonNull OnGetAllFailureListener onGetAllFailureListener);

    @FunctionalInterface
    interface OnNextSuccessListener {
        void onSuccess(@NonNull Ad ad);
    }

    @FunctionalInterface
    interface OnNextFailureListener {
        void onFailure();
    }

    void next(@NonNull OnNextSuccessListener onNextSuccessListener,
              @NonNull OnNextFailureListener onNextFailureListener);
}
