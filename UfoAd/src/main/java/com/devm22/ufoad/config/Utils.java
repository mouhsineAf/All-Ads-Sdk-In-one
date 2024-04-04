package com.devm22.ufoad.config;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.devm22.ufoad.connectionTasks.ConnectionChecker;

public class Utils {

    public static void showToast(Context context, String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    public static void openAdLink(Context context, String link) {

        if (!TextUtils.isEmpty(link) && link != null) {

            if (link.startsWith("http")) {
                //open in internet app :
                openOnInternet(context, link);
            } else {
                // open app with his package name in google play store.
                openOnGooglePlayStore(context, link);
            }

        } else {
            showToast(context, "Failed to get Ad link.");
        }
    }

    public static void openOnGooglePlayStore(Context context, String packageName) {
        try {
            String GGMarket = "market://details?id=";
            Intent intentMarket = new Intent(Intent.ACTION_VIEW, Uri.parse(GGMarket + packageName));
            intentMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentMarket.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                intentMarket.setPackage("com.android.vending");
            } catch (Exception v) {
                v.printStackTrace();
            }
            context.startActivity(intentMarket);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            String GG_APPS = "https://play.google.com/store/apps/details?id=";
            Intent intentStore = new Intent(Intent.ACTION_VIEW, Uri.parse(GG_APPS + packageName));
            intentStore.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentStore.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                intentStore.setPackage("com.android.vending");
            } catch (Exception v) {
                v.printStackTrace();
            }
            if (ConnectionChecker.isConnected(context)) {
                context.startActivity(intentStore);
            } else {
                showToast(context, "Failed to open Ad.");
            }

        }
    }

    public static void openOnInternet(Context context, String link) {
        try {
            Intent intentMarket = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            intentMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentMarket.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intentMarket);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            showToast(context, "Failed to open Ad.");

        }
    }


}
