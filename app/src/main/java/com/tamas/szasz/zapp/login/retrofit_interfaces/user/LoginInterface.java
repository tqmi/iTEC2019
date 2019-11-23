package com.tamas.szasz.zapp.login.retrofit_interfaces.user;

import com.tamas.szasz.zapp.login.retrofit_classes.user.UserLoginRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.user.UserLoginResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginInterface {
    @Headers({
            "TEAM_KEY:SWFMDCMMZBGU8",
            "Content-Type: application/json"})
    @POST("Auth/Login")
    Call<UserLoginResponse> loginUser(@Body UserLoginRequest userLoginRequest);
}

