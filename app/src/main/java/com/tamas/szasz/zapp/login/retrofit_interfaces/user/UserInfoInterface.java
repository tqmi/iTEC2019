package com.tamas.szasz.zapp.login.retrofit_interfaces.user;

import com.tamas.szasz.zapp.login.retrofit_classes.user.UserInfoResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface UserInfoInterface {

    @GET("Auth/Me")
    Call<UserInfoResponse> getUserInfo(@HeaderMap Map<String,String> headers);

}
