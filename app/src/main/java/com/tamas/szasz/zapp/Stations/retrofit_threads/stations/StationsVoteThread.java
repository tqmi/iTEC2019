package com.tamas.szasz.zapp.Stations.retrofit_threads.stations;

import android.util.Log;

import com.tamas.szasz.zapp.Stations.Station;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsVotesResponse;
import com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations.StationsVoteInterface;
import com.tamas.szasz.zapp.retrofit.RetrofitInstance;
import com.tamas.szasz.zapp.retrofit.retrofit_header.Header;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationsVoteThread extends Thread {

    private static final String TAG = "STATIONS VOTE";
    private Station station;
    private boolean value;

    public StationsVoteThread(Station station,boolean value){
        super();
        this.station = station;
        this.value = value;
    }

    @Override
    public void run() {
        StationsVoteInterface stationsVoteInterface = RetrofitInstance.getRetrofitInstance().create(StationsVoteInterface.class);

        String vote;
        if(value)
            vote = "true";
        else
            vote = "false";

        Call<StationsVotesResponse> call = stationsVoteInterface.sendVote(Header.getHeader(),station.getId(),value);
        Log.d(TAG, call.request().toString());
        call.enqueue(new Callback<StationsVotesResponse>() {
            @Override
            public void onResponse(Call<StationsVotesResponse> call, Response<StationsVotesResponse> response) {
                Log.d(TAG,response+"");
                try{
                    Log.d(TAG,response.body().isVote()+"");
                    Log.d(TAG,"success");
                }catch (Exception e){

                    Log.d(TAG,"error");
                    e.printStackTrace();
                    //TODO: handle failed
                }

            }

            @Override
            public void onFailure(Call<StationsVotesResponse> call, Throwable t) {
                Log.d(TAG, "Failure " + t.toString());
                //TODO: handle
            }
        });
    }


}
