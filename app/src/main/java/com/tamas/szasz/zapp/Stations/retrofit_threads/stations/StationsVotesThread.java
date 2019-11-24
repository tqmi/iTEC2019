package com.tamas.szasz.zapp.Stations.retrofit_threads.stations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tamas.szasz.zapp.NavigationActivity;
import com.tamas.szasz.zapp.R;
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
    private NavigationActivity context;
    private String name;
    private int totalSockets;
    private int freeSockets;
    private PointF location;
    private Station station;

    public StationsVotesThread(NavigationActivity context, Station station){
        super();
        this.context = context;
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

                if(response.code() == 200){
                    try{

                        int up = 0 ;
                        int down = 0;
                            for(int i = 0 ; i < response.body().length ; i++ ){

                                if(response.body()[i].isVote()){
                                    up++;
                                }else{
                                    down++;
                                }

                            }
                            station.setDownVotes(down);
                            station.setUpVotes(up);

                            context.showStationPopUp(context.findViewById(R.id.act_navigation_LL), station);

                        Log.d(TAG,"success");
                    }catch (Exception e){

                        Log.d(TAG,"error");
                        e.printStackTrace();
                        //TODO: handle failed
                    }
                }else if(response.code() == 404){
                    station.setDownVotes(0);
                    station.setUpVotes(0);


                    context.showStationPopUp(context.findViewById(R.id.act_navigation_LL), station);

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
