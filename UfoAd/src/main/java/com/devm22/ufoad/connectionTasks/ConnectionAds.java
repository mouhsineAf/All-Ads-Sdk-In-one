package com.devm22.ufoad.connectionTasks;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.devm22.ufoad.UFOAd;
import com.devm22.ufoad.config.UFOAdConfig;
import com.devm22.ufoad.holder.DatabaseBanner;
import com.devm22.ufoad.interfaces.OnConnectAdListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionAds {
    private static final String TAG = "ConnectionAds";

    Activity activity;

    private final String ConnectionBannerAdCache = "BannerAdCache.json";

    private String jsonLink = UFOAdConfig.JsonData;
    private URL url;

    DatabaseBanner databaseBanner;

    OnConnectAdListener connectAdListener;

    public ConnectionAds(Activity activity) {
        this.activity = activity;
        databaseBanner = new DatabaseBanner(activity);
        loadDataFromThread();
    }

    private void loadDataFromThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String task = doInBackgroundTask();
                UFOAd.showLog(TAG, "Thread Connecting Running...");

                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        startConnection(task, true, 0);
                        UFOAd.showLog(TAG, "Thread Finished with" + task);

                    }
                });
            }
        }).start();
    }

    private String doInBackgroundTask() {

        File file = new File(activity.getFilesDir().getPath() + "/" + ConnectionBannerAdCache);
        if (ConnectionChecker.isConnected(activity)) {

            try {
                url = new URL(jsonLink);
            } catch (MalformedURLException e) {
                UFOAd.showLog(TAG, "URL Malformed cause", e.getMessage());
            }

            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");

            } catch (IOException e1) {
                return e1.toString();
            }

            try {

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream inputStream = connection.getInputStream();
                    return buffToString(new InputStreamReader(inputStream), true);

                } else {

                    if (file.exists()) {
                        return buffToString(new FileReader(file), false);
                    }

                }
            } catch (IOException e2) {
                return e2.toString();
            } finally {
                connection.disconnect();
            }

        } else {

            try {
                return buffToString(new FileReader(file), false);
            } catch (IOException e) {
                return e.toString();
            }
        }
        return "";
    }

    private String buffToString(Reader ourReader, boolean save) {
        try {
            BufferedReader reader = new BufferedReader(ourReader);
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            if (save) {
                if (!result.toString().equals(null)) {
                    writeJsonToFile(result.toString());
                }
            }

            return (result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    private void writeJsonToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(activity.openFileOutput(ConnectionBannerAdCache, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startConnection(String result, boolean isThread, int index) {
        switch (index) {
            case 0: //AppsPromote task.
                setAdsFetch(result, isThread);
                break;
            case 1: //YoutubePromote task.

                break;
            case 2: //AppsPromoteWithYoutube task.

                break;
        }
    }

    protected void setAdsFetch(String values, boolean isThread) {

        try {

            JSONObject jsonAdObject = new JSONObject(values);
            JSONObject jsonBannerAdObject = jsonAdObject.getJSONObject(UFOAdConfig.ADS);
            JSONArray jsonBannerAd = jsonBannerAdObject.getJSONArray(UFOAdConfig.BANNER_ADS);

            for (int j = 0; j < jsonBannerAd.length(); j++) {

                JSONObject object = jsonBannerAd.getJSONObject(j);

                int bannerId = object.getInt(UFOAdConfig.AD_ID);
                boolean bannerState = object.getBoolean(UFOAdConfig.AD_STATE);
                String bannerName = object.getString(UFOAdConfig.AD_NAME);
                String bannerDescription = object.getString(UFOAdConfig.AD_DESCRIPTION);
                String bannerBackground = object.getString(UFOAdConfig.AD_BACKGROUND);
                String bannerIcon = object.getString(UFOAdConfig.AD_ICON);
                String bannerUrl = object.getString(UFOAdConfig.AD_URL);
                String bannerActionTitle = object.getString(UFOAdConfig.AD_ACTION_TITLE);
                String bannerType = object.getString(UFOAdConfig.AD_TYPE);
                String bannerKeyword = object.getString(UFOAdConfig.AD_KEYWORD);
                String bannerCountry = object.getString(UFOAdConfig.AD_COUNTRY);
                double bannerECPM = object.getDouble(UFOAdConfig.AD_ECPM);
                int bannerImportance = object.getInt(UFOAdConfig.AD_IMPORTANCE);

                //check if is app Already exist
                if (!databaseBanner.CheckIsDataAlreadyInDB(bannerId)) {
                    //added the values to data base
                    databaseBanner.addBanners(bannerId, bannerState, bannerName, bannerDescription, bannerBackground, bannerIcon,
                            bannerUrl, bannerActionTitle, bannerType, bannerKeyword, bannerCountry, bannerECPM, bannerImportance);


                } else {
                    UFOAd.showLog(TAG, "this items is already added before.");

                }
            }

            if (connectAdListener != null) {
                connectAdListener.onAdConnected();
            }

        } catch (JSONException e) {
            if (connectAdListener != null) {
                connectAdListener.onAdFailed(e.getMessage());
            }

            //reconnect again
            try {
                if (ConnectionChecker.isConnected(activity)) {
                    try {
                        UFOAd.showLog("reconnecting in 5 second !");

                        new CountDownTimer(1000 * 5, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                UFOAd.showLog("reconnecting in 5 second !");

                            }

                            @Override
                            public void onFinish() {
                                if (isThread) {
                                    loadDataFromThread();
                                } else {
                                 //   new setPromotionTask().execute();
                                }
                            }
                        }.start();
                    } catch (Exception countError) {
                        countError.printStackTrace();
                    }

                } else {
                    if (connectAdListener != null) {
                        connectAdListener.onAdFailed("Connecting stopped = user turn-off the connection.");
                    }
                }

            } catch (Exception e2) {
                if (connectAdListener != null) {
                    connectAdListener.onAdFailed(e2.getMessage());
                }
            }

        }

    }



    public void setOnAdConnected(OnConnectAdListener connectAdListener) {
        this.connectAdListener = connectAdListener;
    }

}
