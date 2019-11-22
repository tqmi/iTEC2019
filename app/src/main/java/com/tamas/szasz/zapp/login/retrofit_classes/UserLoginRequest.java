package com.tamas.szasz.zapp.login.retrofit_classes;


public class UserLoginRequest {
    private String email, password;
    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
