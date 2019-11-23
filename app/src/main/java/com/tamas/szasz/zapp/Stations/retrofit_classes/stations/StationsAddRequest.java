package com.tamas.szasz.zapp.Stations.retrofit_classes.stations;

import com.tamas.szasz.zapp.Stations.res.PointF;

public class StationsAddRequest {

    private String name;
    private int totalSockets;
    private int freeSockets;
    private PointF location;

    public StationsAddRequest(String name, int totalSockets, int freeSockets, PointF location) {
        this.name = name;
        this.totalSockets = totalSockets;
        this.freeSockets = freeSockets;
        this.location = location;
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



}
