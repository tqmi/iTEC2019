package com.tamas.szasz.zapp.cars;

public class Car {

    private String model;
    private String company;
    private int year;
    private int autonomy;
    private int batteryLeft;
    private String lastTechRevision;
    private String userId;
    private String id;

    public Car(String model, String company, int year, int autonomy, int batteryLeft, String lastTechRevision, String userId, String id) {
        this.model = model;
        this.company = company;
        this.year = year;
        this.autonomy = autonomy;
        this.batteryLeft = batteryLeft;
        this.lastTechRevision = lastTechRevision;
        this.userId = userId;
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(int autonomy) {
        this.autonomy = autonomy;
    }

    public int getBatteryLeft() {
        return batteryLeft;
    }

    public void setBatteryLeft(int batteryLeft) {
        this.batteryLeft = batteryLeft;
    }

    public String getLastTechRevision() {
        return lastTechRevision;
    }

    public void setLastTechRevision(String lastTechRevision) {
        this.lastTechRevision = lastTechRevision;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
