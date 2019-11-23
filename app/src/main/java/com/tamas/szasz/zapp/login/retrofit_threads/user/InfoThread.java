package com.tamas.szasz.zapp.login.retrofit_threads.user;

import android.util.Log;

import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.login.retrofit_classes.user.UserInfoResponse;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;
import com.tamas.szasz.zapp.login.retrofit_interfaces.user.UserInfoInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoThread extends Thread {
    private static final String TAG = "INFO";

    @Override
    public void run() {
        UserInfoInterface infoInterface = RetrofitInstance.getRetrofitInstance().create(UserInfoInterface.class);

        Call<UserInfoResponse> call = infoInterface.getUserInfo(Header.getHeader());
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                try {
                    Log.d(TAG, "Success" + response.body().getEmail() + " " + response.body().getFirstName() + " " + response.body().getLastName());

                    User.getInstance().setEmail(response.body().getEmail());
                    User.getInstance().setFirstName(response.body().getFirstName());
                    User.getInstance().setLastName(response.body().getLastName());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
            }
        });
    }
}
