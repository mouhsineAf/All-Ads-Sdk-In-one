package com.devm22.ufoad.holder;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.devm22.ufoad.model.BannerModel;
import com.devm22.ufoad.model.User;

import java.util.ArrayList;

public class DatabaseUser extends SQLiteOpenHelper {

    private String CREATE_TABLE;
    private static final String DATABASE_NAME = "bannerAd.sqlite";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "BannerAds";


    private static final String COLUMN_ID = "userId";
    private static final String COLUMN_COUNTRY = "userCountry";
    private static final String COLUMN_COUNTRY_CODE = "userCountryCode";
    private static final String COLUMN_CITY = "userCity";
    private static final String COLUMN_IPS = "userIPS";
    private static final String COLUMN_MAIN_KEYWORD = "userMainKeyword";
    private static final String COLUMN_KEYWORDS = "userKeywords";

    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER,"
                + COLUMN_COUNTRY + " TEXT,"
                + COLUMN_COUNTRY_CODE + " TEXT,"
                + COLUMN_CITY + " TEXT,"
                + COLUMN_IPS + " TEXT,"
                + COLUMN_MAIN_KEYWORD + " TEXT,"
                + COLUMN_KEYWORDS + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void addUserData(int userId, String userCountry, String userCountryCode, String userCity,
                            String userIPS, String userMainKeyword, String userKeywords) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, userId);
        contentValues.put(COLUMN_COUNTRY, userCountry);
        contentValues.put(COLUMN_COUNTRY_CODE, userCountryCode);
        contentValues.put(COLUMN_CITY, userCity);
        contentValues.put(COLUMN_IPS, userIPS);
        contentValues.put(COLUMN_CITY, userMainKeyword);
        contentValues.put(COLUMN_IPS, userKeywords);

        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public ArrayList<User> getUserData() {
        ArrayList<User> appModels = new ArrayList();
        ArrayList<User> appModelsNulls = new ArrayList();
        boolean isNull = true;
        String select = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {

                @SuppressLint("Range")
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range")
                String country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));
                @SuppressLint("Range")
                String countryCode = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_CODE));
                @SuppressLint("Range")
                String city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                @SuppressLint("Range")
                String ips = cursor.getString(cursor.getColumnIndex(COLUMN_IPS));
                @SuppressLint("Range")
                String mainKeyword = cursor.getString(cursor.getColumnIndex(COLUMN_MAIN_KEYWORD));
                @SuppressLint("Range")
                String keywords = cursor.getString(cursor.getColumnIndex(COLUMN_KEYWORDS));

                User user = new User(id, country, countryCode, city, ips, mainKeyword, keywords);
                appModels.add(user);
                isNull = false;
            } while (cursor.moveToNext());
        }
        return isNull ? appModelsNulls : appModels;
    }


}
