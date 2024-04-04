package com.devm22.ufoad;

import android.app.Activity;

import android.util.Log;

import com.devm22.ufoad.connectionTasks.ConnectionAds;
import com.devm22.ufoad.interfaces.OnAdInitializeListener;
import com.devm22.ufoad.interfaces.OnConnectAdListener;
import com.intuit.sdp.BuildConfig;

public class UFOAd {
    private static final String TAG = "UFOAd";

    public static OnAdInitializeListener onAdInitializeListener;


    public static void initializeUFOAd(Activity activity){
        ConnectionAds connectionAds = new ConnectionAds(activity);
        connectionAds.setOnAdConnected(new OnConnectAdListener() {
            @Override
            public void onAdConnected() {
                if (onAdInitializeListener != null){
                    onAdInitializeListener.onInitializeSuccessful();
                }
            }

            @Override
            public void onAdFailed(String error) {
                if (onAdInitializeListener != null){
                    onAdInitializeListener.onInitializeFailed(error);
                }
            }
        });


    }

    public static void setAdInitializeListener(OnAdInitializeListener onAdInitializeListener) {
        UFOAd.onAdInitializeListener = onAdInitializeListener;
    }


    public static void showLog(String msg){
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void showLog(String tag, String msg){
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void showLog(String tag, String name, Object value){
        if (BuildConfig.DEBUG) {
            Log.e(tag, name + " : " + value);
        }
    }



}
