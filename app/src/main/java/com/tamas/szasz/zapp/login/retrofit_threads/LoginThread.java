package com.tamas.szasz.zapp.login.retrofit_threads;

import android.app.Activity;
import android.util.Log;

import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.LoginActivity;
import com.tamas.szasz.zapp.login.retrofit_classes.RetrofitInstance;
import com.tamas.szasz.zapp.login.retrofit_classes.UserLoginRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.UserLoginResponse;
import com.tamas.szasz.zapp.login.retrofit_interfaces.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginThread extends Thread {
    private static final String TAG = "LOGIN";
    private LoginActivity activity;
    private String email;
    private String password;

    public LoginThread(String email,String password, LoginActivity activity){
        super();
        this.email = email;
        this.password = password;
        this.activity = activity;
    }

    @Override
    public void run() {
        LoginInterface loginInterface = RetrofitInstance.getRetrofitInstance().create(LoginInterface.class);

        UserLoginRequest user = new UserLoginRequest(email, password);

        Call<UserLoginResponse> call = loginInterface.loginUser(user);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                try {

                    User.getInstance().setToken(response.body().getToken());
                    activity.loginSuccessful();



                }catch (Exception e){
                    //TODO: handle unsuccessful login
                    activity.loginDenied();
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
                activity.loginDenied();
            }
        });
    }
}