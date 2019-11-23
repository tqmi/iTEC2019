package com.tamas.szasz.zapp.cars.retrofit_classes.cars;

public class CarsAddResponse extends CarsAddRequest {

    private String id;
    private String userId;

    public CarsAddResponse(String model, String company, int year, int autonomy, int batteryLeft, String lastTechRevision, String userId, String id) {
        super(model, company, year, autonomy, batteryLeft, lastTechRevision);
        this.id = id;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
