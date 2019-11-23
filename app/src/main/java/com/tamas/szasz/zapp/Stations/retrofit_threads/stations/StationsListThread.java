package com.tamas.szasz.zapp.Stations.retrofit_threads.stations;

import android.content.Context;
import android.util.Log;

import com.tamas.szasz.zapp.Stations.Station;
import com.tamas.szasz.zapp.Stations.StationHandler;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsListResponse;
import com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations.StationsListInterface;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationsListThread extends Thread {
    private static final String TAG = "STATIONS LIST";
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

                Log.d(TAG,response + "");
                try {

                    ArrayList<Station> arrayList = new ArrayList<>();

                    for(int i = 0 ; i < response.body().length ; i++) {

                        Station s = new Station(response.body()[i]);
                        arrayList.add(s);

                    }

                    StationHandler.getInstance().getStations().retainAll(arrayList);

                    ArrayList<Station> stations = StationHandler.getInstance().getStations();

                    for(int i = 0 ; i < stations.size() ; i++){

                        if(!arrayList.contains(stations.get(i))){
                            stations.get(i).getMarker().remove();
                            stations.remove(i);
                            i--;
                        }

                    }


                    for(int i = 0 ; i < arrayList.size() ; i++) {


                        StationHandler.getInstance().addStation(arrayList.get(i));


                    }

                    Log.d(TAG,"success" + response.body().length);

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
