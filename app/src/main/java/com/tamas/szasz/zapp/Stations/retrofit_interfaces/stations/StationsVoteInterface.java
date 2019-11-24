package com.tamas.szasz.zapp.Stations.retrofit_interfaces.stations;

import com.tamas.szasz.zapp.Stations.retrofit_classes.stations.StationsVotesResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface StationsVoteInterface {


    @GET("Stations/{id}/Vote?newVote={value}")
    Call<StationsVotesResponse> sendVote(@HeaderMap Map<String , String> headers, @Path("id") String id,@Path("value") String value);

}
