package com.example.ridkins.userplaceholder.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Rud on 8/5/17.
 */
public class Geo implements Serializable {
    @SerializedName("lng")
    private Double lng;
    @SerializedName("lat")
    private Double lat;

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLng() {
        return lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLat() {
        return lat;
    }
}