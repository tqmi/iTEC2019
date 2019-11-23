package com.tamas.szasz.zapp.login.retrofit_classes.user;

public class UserLoginResponse extends UserLoginRequest {
    private String token;
    private String errors;

    public UserLoginResponse(String email, String password, String token,String errors) {
        super(email, password);
        this.token = token;
        this.errors = errors;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getToken() {
        return token;
    }

    public void setErrors(String errors){
        this.errors = errors;
    }

    public String getErrors(){
        return errors;
    }
}
