package com.tamas.szasz.zapp.login.retrofit_threads;

import android.util.Log;

import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.retrofit_classes.UserRegistrationResponse;
import com.tamas.szasz.zapp.login.retrofit_interfaces.RegisterInterface;
import com.tamas.szasz.zapp.login.retrofit_classes.RetrofitInstance;
import com.tamas.szasz.zapp.login.retrofit_classes.UserRegistrationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterThread extends Thread{

    private static final String TAG = "REGISTER";

    @Override
    public void run() {
        RegisterInterface registerInterface = RetrofitInstance.getRetrofitInstance().create(RegisterInterface.class);

        UserRegistrationRequest user = new UserRegistrationRequest("davide2@gmail.com", "123456Aa_", "David", "Buzatu");

        Call<UserRegistrationResponse> call = registerInterface.registerUser(user);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<UserRegistrationResponse>() {
            @Override
            public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {
                try {
                    Log.d(TAG, "Success" + response.body().getToken());

                    User.getInstance().setToken(response.body().getToken());
                }catch (Exception e){
                    //TODO: handle unsuccessful register
                }

            }

            @Override
            public void onFailure(Call<UserRegistrationResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
            }
        });
    }
}
