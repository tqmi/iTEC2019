package com.tamas.szasz.zapp.cars.retrofit_interfaces.cars;

import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsAddRequest;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface CarInfoInterface {
    @GET("Cars/{id}")
    Call<CarsListResponse[]> listCars(@HeaderMap Map<String,String> headers, @Path("id") String id);
}
