package com.tamas.szasz.zapp.login.retrofit_interfaces.user;

import com.tamas.szasz.zapp.login.retrofit_classes.user.UserRegistrationRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.user.UserRegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisterInterface {
    @Headers({
            "TEAM_KEY:SWFMDCMMZBGU8",
            "Content-Type: application/json"})
    @POST("Auth/Register")
    Call<UserRegistrationResponse> registerUser(@Body UserRegistrationRequest userRegistrationRequest);
}

