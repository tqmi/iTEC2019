package com.tamas.szasz.zapp.login.retrofit_threads;

import android.util.Log;

import com.tamas.szasz.zapp.login.retrofit_classes.RetrofitInstance;
import com.tamas.szasz.zapp.login.retrofit_classes.UserLoginRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserLoginResponse;
import com.tamas.szasz.zapp.login.retrofit_classes.UserUpdateRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserUpdateResponse;
import com.tamas.szasz.zapp.login.retrofit_interfaces.LoginInterface;
import com.tamas.szasz.zapp.login.retrofit_interfaces.UpdateInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateThread extends Thread {
    private static final String TAG = "UPDATE";

    @Override
    public void run() {
        UpdateInterface updateInterface = RetrofitInstance.getRetrofitInstance().create(UpdateInterface.class);

        UserUpdateRequest user = new UserUpdateRequest("hello", "there");

        Call<UserUpdateResponse> call = updateInterface.updateUser(user);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<UserUpdateResponse>() {
            @Override
            public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
                Log.d(TAG, "Success" + response.body().getFirstName());

                //TODO: extract and save token;
            }

            @Override
            public void onFailure(Call<UserUpdateResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
            }
        });
    }
}
