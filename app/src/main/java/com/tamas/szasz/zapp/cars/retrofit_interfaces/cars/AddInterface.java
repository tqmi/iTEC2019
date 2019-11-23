package com.tamas.szasz.zapp.cars.retrofit_interfaces.cars;

import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsAddRequest;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsAddResponse;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface AddInterface {
    @POST("Cars")
    Call<CarsAddResponse> AddCar(@HeaderMap Map<String,String> headers, @Body CarsAddRequest carsAddRequest );
}
