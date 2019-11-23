package com.tamas.szasz.zapp.login.retrofit_interfaces;

import com.tamas.szasz.zapp.login.retrofit_classes.UserInfoRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserInfoResponse;
import com.tamas.szasz.zapp.login.retrofit_classes.UserRegistrationRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserRegistrationResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserInfoInterface {

    @GET("Auth/Me")
    Call<UserInfoResponse> getUserInfo(@HeaderMap Map<String,String> headers);

}
