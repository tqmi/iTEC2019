package com.tamas.szasz.zapp.Stations.retrofit_threads.stations;

import android.content.Context;
import android.util.Log;

import com.tamas.szasz.zapp.Stations.Station;
import com.tamas.szasz.zapp.Stations.StationHandler;
import com.tamas.szasz.zapp.Stations.res.PointF;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsAddRequest;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsAddResponse;
import com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations.StationsAddInterface;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationsAddThread extends Thread {
    private static final String TAG = "STATIONS ADD";
    private Context context;
    private String name;
    private int totalSockets;
    private int freeSockets;
    private PointF location;


    public StationsAddThread(Context context) {
        super();
        this.context = context;
    }

    public StationsAddThread(String name, int totalSockets, int freeSockets , PointF location){
        super();
        this.name = name;
        this.totalSockets = totalSockets;
        this.freeSockets = freeSockets;
        this.location = location;
    }

    @Override
    public void run() {
        StationsAddInterface stationsAddInterface = RetrofitInstance.getRetrofitInstance().create(StationsAddInterface.class);

        StationsAddRequest stationsAddRequest = new StationsAddRequest(name,totalSockets,freeSockets,location);

        Call<StationsAddResponse> call = stationsAddInterface.addStation(Header.getHeader(),stationsAddRequest);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<StationsAddResponse>() {
            @Override
            public void onResponse(Call<StationsAddResponse> call, Response<StationsAddResponse> response) {
                Log.d(TAG,response+"");
                try {
                    Station s = new Station(response.body().getName(),response.body().getTotalSockets(),response.body().getFreeSockets(),response.body().getLocation(),
                            response.body().getUserId(),response.body().getOldStationId(),response.body().getId());

                    StationHandler.getInstance().addStation(s);
                    Log.d(TAG,"success");
                }catch (Exception e){

                    Log.d(TAG,"error");
                    e.printStackTrace();
                    //TODO: handle failed
                }

            }

            @Override
            public void onFailure(Call<StationsAddResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
                //TODO: handle
            }
        });
    }
}
