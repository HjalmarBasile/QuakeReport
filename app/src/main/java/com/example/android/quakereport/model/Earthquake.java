package com.example.android.quakereport.model;

/**
 * Created by hjalmar
 * On 22/04/2018.
 * <p>
 * An earthquake object
 */
public class Earthquake {

    private final double magnitude;
    private final String place;
    private final long timems;
    private final String url;

    public Earthquake(double magnitude, String place, long timems, String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.timems = timems;
        this.url = url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public long getTimems() {
        return timems;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "magnitude=" + magnitude +
                ", place='" + place + '\'' +
                ", timems=" + timems +
                ", url='" + url + '\'' +
                '}';
    }

}
