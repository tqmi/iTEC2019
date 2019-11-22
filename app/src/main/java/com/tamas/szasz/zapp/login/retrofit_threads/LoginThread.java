package com.tamas.szasz.zapp.login.retrofit_threads;

import android.util.Log;

import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.retrofit_classes.RetrofitInstance;
import com.tamas.szasz.zapp.login.retrofit_classes.UserLoginRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserLoginResponse;
import com.tamas.szasz.zapp.login.retrofit_interfaces.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginThread extends Thread {
    private static final String TAG = "LOGIN";

    @Override
    public void run() {
        LoginInterface loginInterface = RetrofitInstance.getRetrofitInstance().create(LoginInterface.class);

        UserLoginRequest user = new UserLoginRequest("davide@gmail.com", "123456Aa_");

        Call<UserLoginResponse> call = loginInterface.loginUser(user);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                try {
                    Log.d(TAG, "Success" + response.body().getToken());

                    User.getInstance().setToken(response.body().getToken());
                }catch (Exception e){
                    //TODO: handle unsuccessful login
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
            }
        });
    }
}
