package com.prakhar.fooddboy.Model;

public class LocationDataGps {
    private String address;
    private double latitude;
    private double longitude;
    private String datetime; // Date and time field
    private String name; // Date and time field

    // Default constructor required for Firebase
    public LocationDataGps() {
    }

    public LocationDataGps(double latitude, double longitude, String address, String datetime, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.datetime = datetime;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
