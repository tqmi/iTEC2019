package com.tamas.szasz.zapp.cars.retrofit_threads.cars;

import android.content.Context;
import android.util.Log;

import com.tamas.szasz.zapp.cars.retrofit_interfaces.cars.DeleteInterface;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteThread extends Thread {

    private static final String TAG = "DELETE";
    private Context context;
    private String id;

    public DeleteThread(String id,Context context) {
        super();
        this.context = context;
        this.id = id;
    }

    @Override
    public void run() {
        DeleteInterface deleteInterface = RetrofitInstance.getRetrofitInstance().create(DeleteInterface.class);

        Call<String> call = deleteInterface.deleteCar(Header.getHeader(),id);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.d(TAG,response +" ");

                }catch (Exception e){

                    Log.d(TAG,"error");
                    e.printStackTrace();
                    //TODO: handle failed
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());

            }
        });
    }




}
