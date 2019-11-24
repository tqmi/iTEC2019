package com.tamas.szasz.zapp.Stations;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.tamas.szasz.zapp.NavigationActivity;

import java.util.ArrayList;

public class StationHandler {

    private ArrayList<Station> stations;
    private static StationHandler instance;
    private NavigationActivity context;

    public void setContext(NavigationActivity context) {
        this.context = context;
    }

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

        if(!isPartOfStations(s)) {
            stations.add(s);
            s.setMarker(context.addMarker(s.getLatLng()));

        }

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

    public void updateStation(Station s){
        s.getMarker().remove();
        s.setMarker(context.addMarker(s.getLatLng()));
    }

    public Station getStationByMarker(Marker marker){

        for(Station station : stations){
            if(station.getMarker().equals(marker))
                return station;
        }

        return null;

    }

}
