package edu.orangecoastcollege.cs273.dtallcott.connect;

/**
 * Created by HuynhHuu on 30-Nov-17.
 */

public class StudentLocation {
    private double latitude;
    private double longtitude;
    private String lastName;
    private String fullName;

    public StudentLocation(double latitude, double longtitude, String lastName, String fullName) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.lastName = lastName;
        this.fullName = fullName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
