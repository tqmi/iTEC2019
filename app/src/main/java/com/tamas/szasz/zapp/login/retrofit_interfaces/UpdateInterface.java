package com.tamas.szasz.zapp.login.retrofit_interfaces;

import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.retrofit_classes.UserUpdateRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserUpdateResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdateInterface {

    @POST("Auth/Update")
    Call<UserUpdateResponse> updateUser(@HeaderMap Map<String, String> headers, @Body UserUpdateRequest userUpdateRequest);
}
