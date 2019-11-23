package com.tamas.szasz.zapp.login.retrofit_threads.user;

import android.content.Context;
import android.util.Log;

import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.login.retrofit_classes.user.UserUpdateRequest;
import com.tamas.szasz.zapp.login.retrofit_classes.user.UserUpdateResponse;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;
import com.tamas.szasz.zapp.login.retrofit_interfaces.user.UpdateInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateThread extends Thread {
    private static final String TAG = "UPDATE";
    private String firstName = User.getInstance().getFirstName();
    private final Context context;
    private String lastName = User.getInstance().getLastName();

    public UpdateThread(Context context) {
        this.context = context;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public void run() {
        UpdateInterface updateInterface = RetrofitInstance.getRetrofitInstance().create(UpdateInterface.class);

        UserUpdateRequest user = new UserUpdateRequest(firstName, lastName);

        Call<UserUpdateResponse> call = updateInterface.updateUser(Header.getHeader(),user);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<UserUpdateResponse>() {
            @Override
            public void onResponse(Call<UserUpdateResponse> call, Response<UserUpdateResponse> response) {
                try {
                    Log.d(TAG, "Success" + response.body().getFirstName());

                    User.getInstance().setFirstName(firstName);
                    User.getInstance().setLastName(lastName);
                } catch (Exception e) {
                    Log.d("ERR", e.toString());
                }
            }

            @Override
            public void onFailure(Call<UserUpdateResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
            }
        });
    }
}
