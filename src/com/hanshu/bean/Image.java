package com.hanshu.bean;

import java.sql.Timestamp;

public class Image {
    private int ImageID;
    private String title;
    private String description;
    private double longitude;
    private double latitude;
    private int cityCode;
    private String AsciiName;
    private String Country_RegionCodeISO;
    private String Country_RegionName;
    private int uid;
    private String path;
    private String content;
    private Timestamp dateReleased;
    private String username;
    private int favorCount;

    public Image(String title, String description, double longitude, double latitude, int cityCode, String country_RegionCodeISO, int uid, String path, String content, Timestamp dateReleased) {
        this.title = title;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cityCode = cityCode;
        Country_RegionCodeISO = country_RegionCodeISO;
        this.uid = uid;
        this.path = path;
        this.content = content;
        this.dateReleased = dateReleased;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountry_RegionCodeISO() {
        return Country_RegionCodeISO;
    }

    public void setCountry_RegionCodeISO(String country_RegionCodeISO) {
        Country_RegionCodeISO = country_RegionCodeISO;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(Timestamp dateReleased) {
        this.dateReleased = dateReleased;
    }
}
