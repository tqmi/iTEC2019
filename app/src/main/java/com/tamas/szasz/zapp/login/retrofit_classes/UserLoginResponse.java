package com.tamas.szasz.zapp.login.retrofit_classes;

public class UserLoginResponse extends UserLoginRequest {
    private String token;

    public UserLoginResponse(String email, String password, String token) {
        super(email, password);
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getToken() {
        return token;
    }
}
