package com.example.newlocationapp;

public class LocationInfo {
    private long locationId;        // Unique identifier for the location
    private String locationAddress; // Address associated with the location
    private double locationLatitude; // Latitude coordinate
    private double locationLongitude; // Longitude coordinate

    public LocationInfo() {
        // Default constructor
    }

    public LocationInfo(String address, double latitude, double longitude) {
        this.locationAddress = address;
        this.locationLatitude = latitude;
        this.locationLongitude = longitude;
    }

    // Getters and setters for all the fields
    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long id) {
        this.locationId = id;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String address) {
        this.locationAddress = address;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double latitude) {
        this.locationLatitude = latitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double longitude) {
        this.locationLongitude = longitude;
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "locationId=" + locationId +
                ", locationAddress='" + locationAddress + '\'' +
                ", locationLatitude=" + locationLatitude +
                ", locationLongitude=" + locationLongitude +
                '}';
    }
}
