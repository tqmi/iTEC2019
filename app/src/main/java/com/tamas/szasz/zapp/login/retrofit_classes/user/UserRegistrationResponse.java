package com.tamas.szasz.zapp.login.retrofit_classes.user;

public class UserRegistrationResponse extends UserRegistrationRequest {
    private String token;
    private String[] errors;

    public UserRegistrationResponse(String email, String password, String firstName, String lastName , String token, String[] errors) {
        super(email, password, firstName, lastName);
        this.token = token;
        this.errors = errors;
    }

    public void setErrors(String[] errors){
        this.errors = errors;
    }

    public String[] getErrors(){
        return errors;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

}
