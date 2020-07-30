package com.hanshu.bean;

public class Country {
    private String ISO;
    private String fipsCountry_RegionCode;
    private String ISO3;
    private int ISONumeric;
    private String country_RegionName;
    private String capital;
    private int GeoNameID;
    private int area;
    private int population;
    private String continent;
    private String topLevelDomain;
    private String currencyCode;
    private String currencyName;
    private String PhoneCountry_RegionCode;
    private String languages;
    private String postalCodeFormat;
    private String postalCodeRegex;
    private String neighbours;
    private String Country_RegionDescription;

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

}
