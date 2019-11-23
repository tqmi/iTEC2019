package com.tamas.szasz.zapp.login.retrofit_threads.user;

import android.util.Log;

import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.RegisterActivity;
import com.tamas.szasz.zapp.login.retrofit_classes.user.UserRegistrationResponse;
import com.tamas.szasz.zapp.login.retrofit_interfaces.user.RegisterInterface;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.login.retrofit_classes.user.UserRegistrationRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterThread extends Thread{

    private static final String TAG = "USER REGISTER";
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private RegisterActivity context;
    private UserRegistrationRequest user;

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

        user = new UserRegistrationRequest(email, password, firstName, lastName);

        Call<UserRegistrationResponse> call = registerInterface.registerUser(user);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<UserRegistrationResponse>() {
            @Override
            public void onResponse(Call<UserRegistrationResponse> call, Response<UserRegistrationResponse> response) {

                Log.d(TAG,response + " ");

                try {

                    User.getInstance().setToken(response.body().getToken());
                    User.getInstance().setLastName(user.getLastName());
                    User.getInstance().setFirstName(user.getFirstName());
                    User.getInstance().setEmail(user.getEmail());


                    context.onRegisterSuccess();

                    Log.d(TAG,"success");

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
