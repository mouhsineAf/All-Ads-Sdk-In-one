package com.devm22.myadsdkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.devm22.ufoad.BannerAd;
import com.devm22.ufoad.UFOAd;
import com.devm22.ufoad.interfaces.OnAdInitializeListener;
import com.devm22.ufoad.interfaces.OnBannerListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    BannerAd bannerAd;

    FrameLayout layoutBannerAd;

    Button btnRefreshBannerAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layoutBannerAd = findViewById(R.id.frame_banner_ad);
        btnRefreshBannerAd = findViewById(R.id.refreshBannerAd);


        UFOAd.initializeUFOAd(MainActivity.this);
        UFOAd.setAdInitializeListener(new OnAdInitializeListener() {
            @Override
            public void onInitializeSuccessful() {
                Log.e(TAG, "onInitializeSuccessful");
                buildBannerAd();
            }

            @Override
            public void onInitializeFailed(String error) {
                Log.e(TAG, "onInitializeFailed: " + error);

            }
        });

        btnRefreshBannerAd.setOnClickListener(view -> {
            bannerAd.refreshAd();
        });

    }


    private void buildBannerAd() {
        bannerAd = new BannerAd(MainActivity.this);

        bannerAd.setOnBannerListener(new OnBannerListener() {
            @Override
            public void onBannerAdLoaded(View adView) {
                Log.e(TAG, "onBannerAdLoaded: " + adView.toString());
                layoutBannerAd.removeAllViews();
                layoutBannerAd.addView(bannerAd);
            }

            @Override
            public void onBannerAdClicked() {

            }

            @Override
            public void onBannerAdFailedToLoad(String error) {
                Log.e(TAG, "onBannerAdFailedToLoad: " + error);

            }
        });

        bannerAd.loadAd();

    }
}