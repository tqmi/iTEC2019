package com.tamas.szasz.zapp.cars.retrofit_classes.cars;

import org.json.JSONObject;

public class CarsListResponse extends CarsListRequest {

    private JSONObject cars;

    public CarsListResponse(JSONObject cars) {
        super();
        this.cars = cars;
    }

    public JSONObject getCars() {
        return cars;
    }

    public void setCars(JSONObject cars) {
        this.cars = cars;
    }
}
