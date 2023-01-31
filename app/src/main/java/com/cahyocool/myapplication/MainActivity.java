package com.cahyocool.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cahyocool.kafaadslibrary.KafaAds;
import com.cahyocool.kafaadslibrary.models.KafaConst;
import com.cahyocool.kafaadslibrary.models.KafaModel;
import com.cahyocool.kafaadslibrary.utils.ObserverTask;
import com.cahyocool.kafaadslibrary.utils.ProgressTask;
import com.cahyocool.kafaadslibrary.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button btnInters;
    private Button btnRewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("KafaAdsLibrary");

        btnInters = findViewById(R.id.interAds);
        btnRewards = findViewById(R.id.rewaAds);

        btnInters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AdManager(MainActivity.this).showInterAds();
            }
        });
        btnRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AdManager(MainActivity.this).showRewardAds();
            }
        });

        new ProgressTask(this, getAds()).execute();
    }

    private ObserverTask getAds() {
        ObserverTask observer = new ObserverTask() {
            @Override
            public void doInBackground(Object resultObject) {
                RequestHandler rh = new RequestHandler();
                String json = rh.sendGetRequest(Config.adsLink);
                Log.d("DataJson: ", json);
                try {
                    JSONObject jso = new JSONObject(json);
                    JSONArray jsonArray = jso.getJSONArray("Ads");
                    JSONObject jo = jsonArray.getJSONObject(0);
                    KafaModel kafaModel = new KafaModel();
                    kafaModel.set_status(jo.optString(KafaConst.STATUS));
                    kafaModel.set_main_ads(jo.optString(KafaConst.MAIN_ADS));
                    kafaModel.set_backup_ads(jo.optString(KafaConst.BACKUP_ADS));
                    kafaModel.set_backup_status(jo.optString(KafaConst.BACKUP_STATUS));
                    kafaModel.set_appid(jo.optString(KafaConst.APPID));
                    kafaModel.set_banner(jo.optString(KafaConst.BANNER));
                    kafaModel.set_interstitial(jo.optString(KafaConst.INTERSTITIAL));
                    kafaModel.set_reward(jo.optString(KafaConst.REWARD));
                    kafaModel.set_native(jo.optString(KafaConst.NATIVE));
                    kafaModel.set_openapp(jo.optString(KafaConst.OPENAPP));
                    kafaModel.set_backup_appid(jo.optString(KafaConst.BACKUP_APPID));
                    kafaModel.set_backup_banner(jo.optString(KafaConst.BACKUP_BANNER));
                    kafaModel.set_backup_interstitial(jo.optString(KafaConst.BACKUP_INTERSTITIAL));
                    kafaModel.set_backup_reward(jo.optString(KafaConst.BACKUP_REWARD));
                    kafaModel.set_backup_native(jo.optString(KafaConst.BACKUP_NATIVE));
                    kafaModel.set_backup_openapp(jo.optString(KafaConst.BACKUP_OPENAPP));
                    kafaModel.set_app_suspend(jo.optBoolean(KafaConst.APP_SUSPEND));
                    kafaModel.set_link_app_redirect(jo.optString(KafaConst.LINK_APP_REDIRECT));
                    kafaModel.set_interval_ads_inters(jo.getInt(KafaConst.INTERVAL_ADS_INTERS));
                    kafaModel.set_interval_ads_rewa(jo.getInt(KafaConst.INTERVAL_ADS_REWA));
                    kafaModel.set_interval_ads_native(jo.getInt(KafaConst.INTERVAL_ADS_NATIVE));
                    kafaModel.set_unity_test_mode(jo.getBoolean(KafaConst.UNITY_TEST_MODE));
                    KafaAds.setAds(kafaModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPostExecute() {
                KafaAds.initAd(MainActivity.this);
                new AdManager(MainActivity.this).showBanner();
                new AdManager(MainActivity.this).showNative();
            }
        };
        return observer;
    }
}
