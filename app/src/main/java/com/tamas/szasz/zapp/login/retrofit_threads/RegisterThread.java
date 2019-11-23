package com.tamas.szasz.zapp.login.retrofit_threads;

import android.util.Log;

import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.RegisterActivity;
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
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private RegisterActivity context;

    public RegisterThread(String email,String firstName ,String lastName,String password,RegisterActivity context){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.context = context;
    }

    @Override
    public void run() {
        RegisterInterface registerInterface = RetrofitInstance.getRetrofitInstance().create(RegisterInterface.class);

        UserRegistrationRequest user = new UserRegistrationRequest(email, password, firstName, lastName);

        Call<UserRegistrationResponse> call = registerInterface.registerUser(user);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<UserRegistrationResponse>() {
            @Override
            public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {
                try {

                    User.getInstance().setToken(response.body().getToken());

                    context.onRegisterSuccess();

                }catch (Exception e){
                    context.onRegisterFailed();
                    //TODO: handle unsuccessful register
                }

            }

            @Override
            public void onFailure(Call<UserRegistrationResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
                context.onRegisterFailed();
            }
        });
    }
}
