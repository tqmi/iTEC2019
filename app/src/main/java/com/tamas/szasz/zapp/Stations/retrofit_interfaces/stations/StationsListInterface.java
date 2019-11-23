package com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations;

import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsListResponse;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface StationsListInterface {
    @GET("Stations")
    Call<StationsListResponse[]> listStations(@HeaderMap Map<String,String> headers);
}
