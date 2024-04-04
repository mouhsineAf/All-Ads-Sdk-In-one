package com.devm22.ufoad.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.devm22.ufoad.holder.DatabaseUser;
import com.devm22.ufoad.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetUserData {
    private static final String TAG = "GetUserData";

    Context context;

    private DatabaseUser databaseUser;
    private RequestQueue mRequestQueue;
    private ArrayList<User> userArrayList = new ArrayList<>();

    private boolean countryDataIsLoaded = false;
    private int userId;
    private String userCountry;
    private String userCountryCode;
    private String userCity;
    private String userIPS;
    private String userMainKeyword;
    private String userKeywords;


    public GetUserData(Context context) {
        this.context = context;
    }

    public void init() {
        databaseUser = new DatabaseUser(context);


        userArrayList = databaseUser.getUserData();

        if (userArrayList.isEmpty()){
            getDataCountry();
        }


    }


    public void getDataCountry(){
        CustomRequest balanceRequest = new CustomRequest(Request.Method.GET, "http://ip-api.com/json",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            userCountry = response.getString("country");
                            userCountryCode = response.getString("countryCode");
                            userCity = response.getString("city");
                            userIPS = response.getString("isp");

                            countryDataIsLoaded = true;

                        } catch (JSONException e) { //e.printStackTrace();
                            getDataCountry2();
                        }

                    }},new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getDataCountry2();
            }});

        addToRequestQueue(balanceRequest);

    }

    public void getDataCountry2(){
        CustomRequest balanceRequest = new CustomRequest(Request.Method.GET, "https://api.ipgeolocation.io/ipgeo?apiKey=8f522c7726d64b039df9108023f8979b",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            userCountry = response.getString("country_name");
                            userCountryCode = response.getString("country_code2");
                            userCity = response.getString("city");
                            userIPS = response.getString("isp");

                            countryDataIsLoaded = true;

                        } catch (JSONException e) { //e.printStackTrace();
                        }

                    }},new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        addToRequestQueue(balanceRequest);

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}
