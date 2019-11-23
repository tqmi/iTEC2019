package com.tamas.szasz.zapp.Stations;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class StationHandler {

    private ArrayList<Station> stations;
    private static StationHandler instance;

    private StationHandler(){

        stations = new ArrayList<Station>();

    }

    public static StationHandler getInstance(){

        if(instance == null)
            instance = new StationHandler();

        return instance;
    }

    public boolean isPartOfStations(Station s){
        return stations.contains(s);
    }

    public void addStation(Station s){

        if(!isPartOfStations(s))
            stations.add(s);

    }

    public ArrayList<Station> getStations(){
        return stations;
    }

    public ArrayList<LatLng> getPositions(){

        ArrayList<LatLng> pos = new ArrayList<>();

        for(Station station : stations){
            pos.add(station.getLatLng());
        }

        return pos;

    }

}
