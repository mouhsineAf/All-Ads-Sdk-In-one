package com.devm22.ufoad.interfaces;

import android.view.View;

public interface OnBannerListener {
    void onBannerAdLoaded(View adView);
    void onBannerAdClicked();
    void onBannerAdFailedToLoad(String error);
}
