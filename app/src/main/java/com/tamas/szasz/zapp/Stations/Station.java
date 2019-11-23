package com.tamas.szasz.zapp.Stations;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.tamas.szasz.zapp.Stations.res.PointF;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsListResponse;

import java.util.Objects;

public class Station {

    private String name;
    private int totalSockets;
    private int freeSockets;
    private PointF location;
    private String userId;
    private String oldStationId;
    private String id;
    private LatLng latLng;
    private Marker marker;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Station(StationsListResponse station){

        this.name = station.getName();
        this.totalSockets = station.getTotalSockets();
        this.freeSockets = station.getFreeSockets();
        this.location = station.getLocation();
        this.userId = station.getUserId();
        this.oldStationId = station.getOldStationId();
        this.id = station.getId();
        this.latLng = new LatLng(location.getX(),location.getY());

    }

    public Station(String name, int totalSockets, int freeSockets, PointF location, String userId, String oldStationId, String id) {
        this.name = name;
        this.totalSockets = totalSockets;
        this.freeSockets = freeSockets;
        this.location = location;
        this.userId = userId;
        this.oldStationId = oldStationId;
        this.id = id;
        this.latLng = new LatLng(location.getX(),location.getY());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSockets() {
        return totalSockets;
    }

    public void setTotalSockets(int totalSockets) {
        this.totalSockets = totalSockets;
    }

    public int getFreeSockets() {
        return freeSockets;
    }

    public void setFreeSockets(int freeSockets) {
        this.freeSockets = freeSockets;
    }

    public PointF getLocation() {
        return location;
    }

    public void setLocation(PointF location) {
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldStationId() {
        return oldStationId;
    }

    public void setOldStationId(String oldStationId) {
        this.oldStationId = oldStationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return getId().equals(station.getId());
    }

    public LatLng getLatLng(){
        return latLng;
    }

}

