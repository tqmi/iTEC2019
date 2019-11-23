package com.tamas.szasz.zapp.Stations.retrofit_threads.stations;

import android.content.Context;
import android.util.Log;

import com.tamas.szasz.zapp.Stations.Station;
import com.tamas.szasz.zapp.Stations.StationHandler;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsListResponse;
import com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations.StationsListInterface;
import com.tamas.szasz.zapp.cars.Car;
import com.tamas.szasz.zapp.cars.retrofit_classes.cars.CarsListResponse;
import com.tamas.szasz.zapp.cars.retrofit_interfaces.cars.ListInterface;
import com.tamas.szasz.zapp.credentials.User;
import com.tamas.szasz.zapp.login.LoginActivity;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationsListThread extends Thread {
    private static final String TAG = "LIST STATIONS";
    private Context context;

    public StationsListThread(Context context) {
        super();
        this.context = context;
    }

    public StationsListThread(){
        super();
    }

    @Override
    public void run() {
        StationsListInterface stationsListInterface = RetrofitInstance.getRetrofitInstance().create(StationsListInterface.class);

        Call<StationsListResponse[]> call = stationsListInterface.listStations(Header.getHeader());
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<StationsListResponse[]>() {
            @Override
            public void onResponse(Call<StationsListResponse[]> call, Response<StationsListResponse[]> response) {
                try {
                    Log.d(TAG,"Stations list response positive");
                    Log.d(TAG,response + "");

                    ArrayList<Station> arrayList = new ArrayList<>();

                    for(int i = 0 ; i < response.body().length ; i++) {

                        Station s = new Station(response.body()[i]);
                        arrayList.add(s);

                    }

                    StationHandler.getInstance().getStations().retainAll(arrayList);

                    for(int i = 0 ; i < arrayList.size() ; i++) {


                        StationHandler.getInstance().addStation(arrayList.get(i));


                    }


                }catch (Exception e){

                    Log.d(TAG,"error");
                    e.printStackTrace();
                    //TODO: handle failed
                }

            }

            @Override
            public void onFailure(Call<StationsListResponse[]> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
                //TODO: handle
            }
        });
    }
}
