package com.tamas.szasz.zapp.cars.retrofit_interfaces.cars;

import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsListResponse;
import com.tamas.szasz.zapp.login.retrofit_classes.user.UserInfoResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface ListInterface {
    @GET("Auth/Cars")
    Call<CarsListResponse> listCars(@HeaderMap Map<String,String> headers);
}
