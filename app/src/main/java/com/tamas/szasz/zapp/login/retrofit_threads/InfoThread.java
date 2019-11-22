package com.tamas.szasz.zapp.login.retrofit_threads;

import android.util.Log;

import com.tamas.szasz.zapp.login.retrofit_classes.RetrofitInstance;
import com.tamas.szasz.zapp.login.retrofit_classes.UserInfoRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserInfoResponse;
import com.tamas.szasz.zapp.login.retrofit_classes.UserLoginRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserLoginResponse;
import com.tamas.szasz.zapp.login.retrofit_interfaces.LoginInterface;
import com.tamas.szasz.zapp.login.retrofit_interfaces.UserInfoInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoThread extends Thread {
    private static final String TAG = "INFO";

    @Override
    public void run() {
        UserInfoInterface infoInterface = RetrofitInstance.getRetrofitInstance().create(UserInfoInterface.class);

        Call<UserInfoResponse> call = infoInterface.getUserInfo();
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                Log.d(TAG, "Success" + response.body().getEmail()+" "+response.body().getFirstName()+" "+response.body().getLastName());

                //TODO: extract and save INFO;
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
            }
        });
    }
}
