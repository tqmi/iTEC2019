package com.tamas.szasz.zapp.Stations.retrofit_classes.stations;

import com.tamas.szasz.zapp.Stations.Station;

public class StationsVotesResponse {

    private boolean vote;
    private String userId;
    private String stationId;
    private String id;


    public StationsVotesResponse(boolean vote, String userId, String stationId, String id) {
        this.vote = vote;
        this.userId = userId;
        this.stationId = stationId;
        this.id = id;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
