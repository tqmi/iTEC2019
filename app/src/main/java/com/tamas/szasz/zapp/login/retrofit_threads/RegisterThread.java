package com.tamas.szasz.zapp.login.retrofit_threads;

import android.util.Log;

import com.tamas.szasz.zapp.login.retrofit_interfaces.RegisterInterface;
import com.tamas.szasz.zapp.login.retrofit_classes.RetrofitInstance;
import com.tamas.szasz.zapp.login.retrofit_classes.UserRegistrationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterThread extends Thread{

        public void run() {
            RegisterInterface registerInterface = RetrofitInstance.getRetrofitInstance().create(RegisterInterface.class);
            JSONObject obj = new JSONObject();
            JSONObject objDetails = new JSONObject();
            try {
                obj.put("email", "davide@gmail.com");
                obj.put("password", "123456Aa_");
                obj.put("firstName", "David");
                obj.put("lastName", "Buzatu");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            UserRegistrationRequest user = new UserRegistrationRequest("davide@gmail.com", "123456Aa_", "David", "Buzatu");

            Call<UserRegistrationRequest> call = registerInterface.registerUser(user);
            Log.d("REGISTER", call.request().toString());
            call.enqueue(new Callback<UserRegistrationRequest>() {
                @Override
                public void onResponse(Call<UserRegistrationRequest> call, Response<UserRegistrationRequest> response) {
                    Log.d("REGISTER", "Success" + response);
                }

                @Override
                public void onFailure(Call<UserRegistrationRequest> call, Throwable t) {
                    Log.d("REGISTER", "Failure " + t.toString());
                }
            });
        }
}
