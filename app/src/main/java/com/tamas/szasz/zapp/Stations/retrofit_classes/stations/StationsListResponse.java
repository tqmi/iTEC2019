package com.tamas.szasz.zapp.Stations.retrofit_classes.stations;

import com.tamas.szasz.zapp.Stations.res.PointF;

public class StationsListResponse {

    private String name;
    private int totalSockets;
    private int freeSockets;
    private PointF location;
    private String userId;
    private String oldStationId;
    private String id;

    public StationsListResponse(String name, int totalSockets, int freeSockets, PointF location, String userId, String oldStationId, String id) {
        this.name = name;
        this.totalSockets = totalSockets;
        this.freeSockets = freeSockets;
        this.location = location;
        this.userId = userId;
        this.oldStationId = oldStationId;
        this.id = id;
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
}
