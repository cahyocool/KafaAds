package com.cahyocool.kafaadslibrary.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;

public class ProgressTask extends AsyncTask<String, Void, Boolean> {
    private ProgressDialog dialog;
    private Context context;
    private ObserverTask observer;

    public ProgressTask(Activity activity, ObserverTask observer) {
        this.observer = observer;
        this.context = activity;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        Log.v("ProgressTask", "start on pre execute");
        this.dialog.setMessage("Preparing data...");
        this.dialog.show();
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        Log.v("ProgressTask", "start on post execute");
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (observer != null) {
            observer.onPostExecute();
        }
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            Log.v("ProgressTask", String.format("strings doInBackground %s", Arrays.toString(strings)));
            if (observer != null) {
                observer.doInBackground(null);
            }
            Log.v("ProgressTask", "finish execute do in background");
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("sticker or internet connection is wrong!", e);
        }

        return null;
    }
}
