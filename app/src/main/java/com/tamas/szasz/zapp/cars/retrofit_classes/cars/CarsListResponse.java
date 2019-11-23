package com.tamas.szasz.zapp.cars.retrofit_classes.cars;

import com.tamas.szasz.zapp.cars.Car;

import org.json.JSONObject;

public class CarsListResponse extends CarsListRequest {

    public CarsListResponse(String model, String company, int year, int autonomy, int batteryLeft, String lastTechRevision, String userId, String id) {
        super(model, company, year, autonomy, batteryLeft, lastTechRevision, userId, id);
    }

}
