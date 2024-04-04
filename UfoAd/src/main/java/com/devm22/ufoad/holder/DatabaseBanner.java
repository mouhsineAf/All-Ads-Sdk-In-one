package com.devm22.ufoad.holder;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devm22.ufoad.model.BannerModel;

import java.util.ArrayList;

public class DatabaseBanner extends SQLiteOpenHelper {

    private String CREATE_TABLE;
    private static final String DATABASE_NAME = "bannerAd.sqlite";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "BannerAds";


    private static final String COLUMN_ID = "bannerId";
    private static final String COLUMN_STATE = "bannerState";
    private static final String COLUMN_NAME = "bannerName";
    private static final String COLUMN_DESCRIPTION = "bannerDescription";
    private static final String COLUMN_BACKGROUND = "bannerBackground";
    private static final String COLUMN_ICON = "bannerIcon";
    private static final String COLUMN_URL = "bannerUrl";
    private static final String COLUMN_ACTION_TITLE = "bannerActionTitle";
    private static final String COLUMN_TYPE = "bannerType";
    private static final String COLUMN_KEYWORD = "bannerKeyword";
    private static final String COLUMN_COUNTRY = "bannerCountry";
    private static final String COLUMN_ECPM = "bannerECPM";
    private static final String COLUMN_IMPORTANCE = "bannerImportance";



    public DatabaseBanner(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER,"
                + COLUMN_STATE + " INTEGER,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_BACKGROUND + " TEXT,"
                + COLUMN_ICON + " TEXT,"
                + COLUMN_URL + " TEXT,"
                + COLUMN_ACTION_TITLE + " TEXT,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_KEYWORD + " TEXT,"
                + COLUMN_COUNTRY + " TEXT,"
                + COLUMN_ECPM + " REAL,"
                + COLUMN_IMPORTANCE + " INTEGER"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addBanners(int bannerId, boolean bannerState, String bannerName, String bannerDescription, String bannerBackground, String bannerIcon,
                           String bannerUrl, String bannerActionTitle, String bannerType, String bannerKeyword, String bannerCountry, double bannerECPM,
                           int bannerImportance) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, bannerId);
        contentValues.put(COLUMN_STATE, bannerState);
        contentValues.put(COLUMN_NAME, bannerName);
        contentValues.put(COLUMN_DESCRIPTION, bannerDescription);
        contentValues.put(COLUMN_BACKGROUND, bannerBackground);
        contentValues.put(COLUMN_ICON, bannerIcon);
        contentValues.put(COLUMN_URL, bannerUrl);
        contentValues.put(COLUMN_ACTION_TITLE, bannerActionTitle);
        contentValues.put(COLUMN_TYPE, bannerType);
        contentValues.put(COLUMN_KEYWORD, bannerKeyword);
        contentValues.put(COLUMN_COUNTRY, bannerCountry);
        contentValues.put(COLUMN_ECPM, bannerECPM);
        contentValues.put(COLUMN_IMPORTANCE, bannerImportance);

        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }


    public int getNumberOfTable() {
        return (int) DatabaseUtils.queryNumEntries(getWritableDatabase(), TABLE_NAME);
    }

    public boolean CheckIsDataAlreadyInDB(int valueId) {
        String Query = "Select * from " + TABLE_NAME + " where " + COLUMN_ID + " = " + valueId;
        Cursor cursor = getReadableDatabase().rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public ArrayList<BannerModel> getAllBanners() {
        ArrayList<BannerModel> appModels = new ArrayList();
        ArrayList<BannerModel> appModelsNulls = new ArrayList();
        boolean isNull = true;
        String select = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {

                @SuppressLint("Range")
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range")
                int st = cursor.getInt(cursor.getColumnIndex(COLUMN_STATE));
                boolean state = false;
                if (st == 1){
                    state = true;
                }
                @SuppressLint("Range")
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range")
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range")
                String background = cursor.getString(cursor.getColumnIndex(COLUMN_BACKGROUND));
                @SuppressLint("Range")
                String icon = cursor.getString(cursor.getColumnIndex(COLUMN_ICON));
                @SuppressLint("Range")
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                @SuppressLint("Range")
                String ActionTitle = cursor.getString(cursor.getColumnIndex(COLUMN_ACTION_TITLE));
                @SuppressLint("Range")
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                @SuppressLint("Range")
                String keyword = cursor.getString(cursor.getColumnIndex(COLUMN_KEYWORD));
                @SuppressLint("Range")
                String country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));
                @SuppressLint("Range")
                double ecpm = cursor.getDouble(cursor.getColumnIndex(COLUMN_ECPM));
                @SuppressLint("Range")
                int importance = cursor.getInt(cursor.getColumnIndex(COLUMN_IMPORTANCE));


                BannerModel apps = new BannerModel(id, state, name, description, background, icon, url, ActionTitle, type, keyword, country, ecpm, importance);
                appModels.add(apps);
                isNull = false;
            } while (cursor.moveToNext());
        }
        return isNull ? appModelsNulls : appModels;
    }


}
