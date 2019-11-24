package com.tamas.szasz.zapp.Stations.retrofit_threads.stations;

import android.content.Context;
import android.util.Log;

import com.tamas.szasz.zapp.Stations.Station;
import com.tamas.szasz.zapp.Stations.StationHandler;
import com.tamas.szasz.zapp.Stations.res.PointF;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsAddRequest;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsAddResponse;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsVotesResponse;
import com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations.StationsAddInterface;
import com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations.StationsVotesInterface;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationsVotesThread extends Thread {

    private static final String TAG = "STATIONS VOTES";
    private Context context;
    private String name;
    private int totalSockets;
    private int freeSockets;
    private PointF location;
    private Station station;

    public StationsVotesThread(Context context) {
        super();
        this.context = context;
    }

    public StationsVotesThread(Station station){
        super();
        this.station = station;
    }

    @Override
    public void run() {
        StationsVotesInterface stationsVotesInterface = RetrofitInstance.getRetrofitInstance().create(StationsVotesInterface.class);


        Call<StationsVotesResponse[]> call = stationsVotesInterface.getVotes(Header.getHeader(),station.getId());
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<StationsVotesResponse[]>() {
            @Override
            public void onResponse(Call<StationsVotesResponse[]> call, Response<StationsVotesResponse[]> response) {
                Log.d(TAG,response+"");
                try{

                        for(int i = 0 ; i < response.body().length ; i++ ){

                            if(response.body()[i].isVote()){
                                station.setUpVotes(station.getUpVotes() + 1);
                            }else{
                                station.setDownVotes(station.getDownVotes() + 1);
                            }



                        }
                    Log.d(TAG,"success");
                }catch (Exception e){

                    Log.d(TAG,"error");
                    e.printStackTrace();
                    //TODO: handle failed
                }

            }

            @Override
            public void onFailure(Call<StationsVotesResponse[]> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
                //TODO: handle
            }
        });
    }

}
