package com.tamas.szasz.zapp.cars.retrofit_threads.cars;

import android.content.Context;
import android.util.Log;

import com.tamas.szasz.zapp.cars.Car;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarUpdateRequest;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarUpdateResponse;
import com.tamas.szasz.zapp.cars.retrofit_interfaces.cars.CarUpdateInterface;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarUpdateThread extends Thread {

    private static final String TAG = "CAR UPDATE";
    private Context context;
    private Car car;

    public CarUpdateThread(Car car, Context context) {
        super();
        this.context = context;
        this.car = car;
    }



    @Override
    public void run() {
        CarUpdateInterface carUpdateInterface = RetrofitInstance.getRetrofitInstance().create(CarUpdateInterface.class);

        CarUpdateRequest carUpdateRequest = new CarUpdateRequest(car.getModel(),car.getCompany(),car.getYear(),car.getAutonomy(),car.getBatteryLeft(),car.getLastTechRevision());

        Call<CarUpdateResponse> call = carUpdateInterface.deleteCar(Header.getHeader(),carUpdateRequest,car.getId());
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<CarUpdateResponse>() {
            @Override
            public void onResponse(Call<CarUpdateResponse> call, Response<CarUpdateResponse> response) {
                Log.d(TAG,response +" ");
                try {
                    car.setCompany(response.body().getCompany());
                    car.setAutonomy(response.body().getAutonomy());
                    car.setBatteryLeft(response.body().getBatteryLeft());
                    car.setLastTechRevision(response.body().getLastTechRevision());
                    car.setModel(response.body().getModel());
                    car.setUserId(response.body().getUserId());
                    car.setId(response.body().getId());
                    Log.d(TAG,"success");
                }catch (Exception e){

                    Log.d(TAG,"error");
                    e.printStackTrace();
                    //TODO: handle failed
                }

            }

            @Override
            public void onFailure(Call<CarUpdateResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());

            }
        });
    }

}
