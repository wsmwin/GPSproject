package com.oss.kookmin.gps;
//와드 박을 때 위도와 경도를 묶어줄 수 있도록 클레스 생성
public class LatLon {
    double lat; // 위도
    double lon; // 경도
    public LatLon(double nLat, double nLon)
    {
        lat = nLat; lon = nLon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}