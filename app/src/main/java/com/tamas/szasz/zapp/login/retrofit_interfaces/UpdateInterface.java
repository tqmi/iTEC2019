package com.tamas.szasz.zapp.login.retrofit_interfaces;

import com.tamas.szasz.zapp.login.retrofit_classes.UserRegistrationRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserRegistrationResponse;
import com.tamas.szasz.zapp.login.retrofit_classes.UserUpdateRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserUpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdateInterface {
    @Headers({
            "TEAM_KEY:SWFMDCMMZBGU8",
            "Content-Type: application/json",
            "Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImp0aSI6ImRhMzkwMGQ3LWUyNmUtNDdmMi1hOTk1LTkyYjViNzZmMGYxNyIsImVtYWlsIjoidGVzdEBnbWFpbC5jb20iLCJpZCI6ImJjODNjNWVmLWU0NDAtNDk0MC1hY2RlLTRhYjcyYWJjOTQ5YyIsIm5iZiI6MTU3NDQzNjU0MSwiZXhwIjoxNTc0Njk1NzQxLCJpYXQiOjE1NzQ0MzY1NDF9.5SVjBedzR4hiPaXMqLsdmxfh2mhCUvZPV7fDi6nwtMg"})
    @POST("Auth/Update")
    Call<UserUpdateResponse> updateUser(@Body UserUpdateRequest userUpdateRequest);
}
