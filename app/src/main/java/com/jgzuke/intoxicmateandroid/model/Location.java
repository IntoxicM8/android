package com.jgzuke.intoxicmateandroid.model;

/**
 * Used by the user activity endpoint and contains information including the latitude, longitude &
 * address of a location.
 */
public class Location extends UberModel {

    /**
     * Latitude component of location.
     */
    double latitude;

    public double getLatitude() {
        return latitude;
    }

    /**
     * Longitude component of location.
     */
    double longitude;

    public double getLongitude() {
        return longitude;
    }
    
    int bearing;
    
    public int getBearing() { return bearing; }

}
