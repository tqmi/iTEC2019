package com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations;

import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsAddRequest;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsAddResponse;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface StationsAddInterface {
    @GET("Stations")
    Call<StationsAddResponse> addStation(@HeaderMap Map<String,String> headers, @Body StationsAddRequest stationsAddRequest);
}
