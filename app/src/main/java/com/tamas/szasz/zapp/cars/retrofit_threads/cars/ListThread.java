package com.tamas.szasz.zapp.cars.retrofit_threads.cars;

import android.util.Log;

import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsListResponse;
import com.tamas.szasz.zapp.cars.retrofit_interfaces.cars.ListInterface;
import com.tamas.szasz.zapp.login.RegisterActivity;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListThread extends Thread {
    private static final String TAG = "LIST";
    private RegisterActivity context;

    public ListThread(RegisterActivity context) {
        this.context = context;
    }

    @Override
    public void run() {
        ListInterface listInterface = RetrofitInstance.getRetrofitInstance().create(ListInterface.class);

        Call<CarsListResponse> call = listInterface.listCars(Header.getHeader());
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<CarsListResponse>() {
            @Override
            public void onResponse(Call<CarsListResponse> call, Response<CarsListResponse> response) {
                try {
                    Log.d(TAG,response.body().getCars().toString());
                }catch (Exception e){
                    //TODO: handle failed
                }

            }

            @Override
            public void onFailure(Call<CarsListResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());

            }
        });
    }
}
