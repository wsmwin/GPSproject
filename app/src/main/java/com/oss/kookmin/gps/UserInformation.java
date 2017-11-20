package com.oss.kookmin.gps;

/**
 * Created by Woo on 2017-11-20.
 */

public class UserInformation {

    public String name;
    public double latitude;
    public double longitude;

    public UserInformation(){

    }
    public UserInformation(String name, double latitude, double longitude){
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;
    }
}
