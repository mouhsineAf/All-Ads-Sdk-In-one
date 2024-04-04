package com.devm22.ufoad.model;

public class BannerModel {
    private int bannerId;
    private boolean bannerState;
    private String bannerName;
    private String bannerDescription;
    private String bannerBackground;
    private String bannerIcon;
    private String bannerUrl;
    private String bannerActionTitle;
    private String bannerType;
    private String bannerKeyword;
    private String bannerCountry;
    private double bannerECPM;
    private int bannerImportance;


    public BannerModel(int bannerId, boolean bannerState, String bannerName, String bannerDescription, String bannerBackground, String bannerIcon, String bannerUrl, String bannerActionTitle, String bannerType, String bannerKeyword, String bannerCountry, double bannerECPM, int bannerImportance) {
        this.bannerId = bannerId;
        this.bannerState = bannerState;
        this.bannerName = bannerName;
        this.bannerDescription = bannerDescription;
        this.bannerBackground = bannerBackground;
        this.bannerIcon = bannerIcon;
        this.bannerUrl = bannerUrl;
        this.bannerActionTitle = bannerActionTitle;
        this.bannerType = bannerType;
        this.bannerKeyword = bannerKeyword;
        this.bannerCountry = bannerCountry;
        this.bannerECPM = bannerECPM;
        this.bannerImportance = bannerImportance;
    }

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public boolean isBannerState() {
        return bannerState;
    }

    public void setBannerState(boolean bannerState) {
        this.bannerState = bannerState;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerDescription() {
        return bannerDescription;
    }

    public void setBannerDescription(String bannerDescription) {
        this.bannerDescription = bannerDescription;
    }

    public String getBannerBackground() {
        return bannerBackground;
    }

    public void setBannerBackground(String bannerBackground) {
        this.bannerBackground = bannerBackground;
    }

    public String getBannerIcon() {
        return bannerIcon;
    }

    public void setBannerIcon(String bannerIcon) {
        this.bannerIcon = bannerIcon;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getBannerActionTitle() {
        return bannerActionTitle;
    }

    public void setBannerActionTitle(String bannerActionTitle) {
        this.bannerActionTitle = bannerActionTitle;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public String getBannerKeyword() {
        return bannerKeyword;
    }

    public void setBannerKeyword(String bannerKeyword) {
        this.bannerKeyword = bannerKeyword;
    }

    public String getBannerCountry() {
        return bannerCountry;
    }

    public void setBannerCountry(String bannerCountry) {
        this.bannerCountry = bannerCountry;
    }

    public double getBannerECPM() {
        return bannerECPM;
    }

    public void setBannerECPM(double bannerECPM) {
        this.bannerECPM = bannerECPM;
    }

    public int getBannerImportance() {
        return bannerImportance;
    }

    public void setBannerImportance(int bannerImportance) {
        this.bannerImportance = bannerImportance;
    }
}
