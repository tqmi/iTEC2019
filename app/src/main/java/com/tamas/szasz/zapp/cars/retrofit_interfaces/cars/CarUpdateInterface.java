package com.tamas.szasz.zapp.cars.retrofit_interfaces.cars;

import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarUpdateRequest;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarUpdateResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface CarUpdateInterface {
    @PATCH("Cars/{id}")
    Call<CarUpdateResponse> deleteCar(@HeaderMap Map<String,String> headers, @Body CarUpdateRequest carUpdateRequest, @Path("id") String id);

}
