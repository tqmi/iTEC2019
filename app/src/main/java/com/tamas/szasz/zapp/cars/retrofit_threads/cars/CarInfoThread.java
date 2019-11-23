package com.tamas.szasz.zapp.cars.retrofit_threads.cars;

import android.content.Context;
import android.util.Log;

import com.tamas.szasz.zapp.cars.Car;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsAddRequest;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsAddResponse;
import com.tamas.szasz.zapp.cars.retrofit_interfaces.cars.AddInterface;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarInfoThread extends Thread {

    private static final String TAG = "CAR INFO";
    private Context context;
    private CarsAddRequest car;

    public CarInfoThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        AddInterface addInterface = RetrofitInstance.getRetrofitInstance().create(AddInterface.class);

        Call<CarsAddResponse> call = addInterface.AddCar(Header.getHeader(),car);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<CarsAddResponse>() {
            @Override
            public void onResponse(Call<CarsAddResponse> call, Response<CarsAddResponse> response) {
                Log.d(TAG,response + "");
                try {
                    User.getInstance().addCar(new Car(
                            response.body().getModel(),
                            response.body().getCompany(),
                            response.body().getYear(),
                            response.body().getAutonomy(),
                            response.body().getBatteryLeft(),
                            response.body().getLastTechRevision(),
                            response.body().getUserId(),
                            response.body().getId()
                    ));
                    Log.d(TAG,"success");
                }catch (Exception e){

                    Log.d(TAG,"error");
                    e.printStackTrace();
                    //TODO: handle failed
                }

            }

            @Override
            public void onFailure(Call<CarsAddResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());

            }
        });
    }


}
