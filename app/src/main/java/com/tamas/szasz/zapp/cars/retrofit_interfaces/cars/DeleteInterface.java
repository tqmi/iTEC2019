package com.tamas.szasz.zapp.cars.retrofit_interfaces.cars;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface DeleteInterface {
    @DELETE("Cars/{id}")
    Call<String> deleteCar(@HeaderMap Map<String,String> headers, @Path("id") String id);

}
