package com.tamas.szasz.zapp.Stations.retrofit_classes.stations;

import com.tamas.szasz.zapp.Stations.res.PointF;

public class StationsAddResponse extends StationsAddRequest {


    private String userId;
    private String oldStationId;
    private String id;

    public StationsAddResponse(String name, int totalSockets, int freeSockets, PointF location, String userId, String oldStationId, String id) {
        super(name, totalSockets, freeSockets, location);
        this.userId = userId;
        this.oldStationId = oldStationId;
        this.id = id;
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
