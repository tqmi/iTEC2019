package com.tamas.szasz.zapp.login.retrofit_interfaces.user;

import com.tamas.szasz.zapp.login.retrofit_classes.user.UserUpdateRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.user.UserUpdateResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface UpdateInterface {

    @POST("Auth/Update")
    Call<UserUpdateResponse> updateUser(@HeaderMap Map<String, String> headers, @Body UserUpdateRequest userUpdateRequest);
}
