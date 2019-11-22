package com.tamas.szasz.zapp.login.retrofit_interfaces;

import com.tamas.szasz.zapp.login.retrofit_classes.UserRegistrationRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserRegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisterInterface {
    @Headers({
            "TEAM_KEY:SWFMDCMMZBGU8",
            "Content-Type: application/json"})
    @POST("Auth/Register")
    Call<UserRegistrationResponse> registerUser(@Body UserRegistrationRequest userRegistrationRequest);
}

