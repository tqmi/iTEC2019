package com.tamas.szasz.zapp.login.retrofit_classes;

public class BadRequestResult {
    private int statusCode;

    public BadRequestResult(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
