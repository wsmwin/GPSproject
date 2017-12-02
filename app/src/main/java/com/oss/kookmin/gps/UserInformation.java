package com.oss.kookmin.gps;
//와드를 박을 때 쓰일 UserInformation클레스이다.
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
