package com.tamas.szasz.zapp.Stations.res;

public class PointF {

    private boolean isEmpty;
    private double x;
    private double y;

    public PointF(boolean isEmpty, double x, double y) {
        this.isEmpty = isEmpty;
        this.x = x;
        this.y = y;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
