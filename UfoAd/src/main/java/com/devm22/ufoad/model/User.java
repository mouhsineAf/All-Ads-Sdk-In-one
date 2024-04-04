package com.devm22.ufoad.model;

public class User {
    private int userId;
    private String userCountry;
    private String userCountryCode;
    private String userCity;
    private String userIPS;
    private String userMainKeyword;
    private String userKeywords;

    public User(int userId, String userCountry, String userCountryCode, String userCity, String userIPS, String userMainKeyword, String userKeywords) {
        this.userId = userId;
        this.userCountry = userCountry;
        this.userCountryCode = userCountryCode;
        this.userCity = userCity;
        this.userIPS = userIPS;
        this.userMainKeyword = userMainKeyword;
        this.userKeywords = userKeywords;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserCountryCode() {
        return userCountryCode;
    }

    public void setUserCountryCode(String userCountryCode) {
        this.userCountryCode = userCountryCode;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserIPS() {
        return userIPS;
    }

    public void setUserIPS(String userIPS) {
        this.userIPS = userIPS;
    }

    public String getUserMainKeyword() {
        return userMainKeyword;
    }

    public void setUserMainKeyword(String userMainKeyword) {
        this.userMainKeyword = userMainKeyword;
    }

    public String getUserKeywords() {
        return userKeywords;
    }

    public void setUserKeywords(String userKeywords) {
        this.userKeywords = userKeywords;
    }
}
