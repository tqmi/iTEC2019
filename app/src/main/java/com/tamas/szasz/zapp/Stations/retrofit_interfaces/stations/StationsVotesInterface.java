package com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations;

import com.tamas.szasz.zapp.Stations.Station;
import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsVotesResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface StationsVotesInterface {

    @GET("Stations/{id}/Votes")
    Call<StationsVotesResponse[]> getVotes(@HeaderMap Map<String , String> headers, @Path("id") String id);

}
