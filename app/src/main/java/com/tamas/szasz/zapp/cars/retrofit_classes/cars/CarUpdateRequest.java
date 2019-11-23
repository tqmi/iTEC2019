package com.tamas.szasz.zapp.cars.retrofit_classes.cars;

public class CarUpdateRequest {

    private String model;
    private String company;
    private int year;
    private int autonomy;
    private int batteryLeft;
    private String lastTechRevision; //can be parsed as localdatetime

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

    public CarUpdateRequest(String model, String company, int year, int autonomy, int batteryLeft, String lastTechRevision) {
        this.model = model;
        this.company = company;
        this.year = year;
        this.autonomy = autonomy;
        this.batteryLeft = batteryLeft;
        this.lastTechRevision = lastTechRevision;
    }



}
